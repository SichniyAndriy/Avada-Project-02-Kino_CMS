package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.dto.TheaterPictureDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import avada.spacelab.kino_cms.model.mapper.TheaterMapper;
import avada.spacelab.kino_cms.model.mapper.TheaterPictureMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.TheaterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final ScheduleRepository scheduleRepository;

    public TheaterServiceImpl(
            TheaterRepository theaterRepository,
            AuditoriumRepository auditoriumRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Theater::getId))
                .map(TheaterMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public TheaterDto getById(long id) {
        Optional<Theater> optionalTheater = theaterRepository.findById(id);
        if (optionalTheater.isPresent()) {
            Theater theater = optionalTheater.get();
            if (theater.getSeoBlock() == null) {
                theater.setSeoBlock(new SeoBlock());
            }
            return TheaterMapper.INSTANCE.fromEntityToDto(theater);
        }
        return TheaterDto.EMPTY();
    }

    public TheaterDto save(TheaterDto theaterDto, String picturesJson) {
        List<TheaterPictureDto> theaterPictureDtos;
        try {
            theaterPictureDtos = new ObjectMapper()
                    .readValue(picturesJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<TheaterPicture> pictures = theaterPictureDtos.stream()
                .map(TheaterPictureMapper.INSTANCE::fromDtoToEntity)
                .toList();

        Theater theater = TheaterMapper.INSTANCE.fromDtoToEntity(theaterDto);
        if (theater.getSeoBlock() == null) {
            theater.setSeoBlock(new SeoBlock());
        }

        theater.setPictures(pictures);
        pictures.forEach(picture -> picture.setTheater(theater));

        List<Auditorium> auditoriums = auditoriumRepository.findAuditoriumsByTheaterId(theater.getId());
        theater.setAuditoriums(auditoriums);
        auditoriums.forEach(auditorium -> auditorium.setTheater(theater));

        Theater saved = theaterRepository.save(theater);
        return TheaterMapper.INSTANCE.fromEntityToDto(saved);
    }

    public void deleteById(long id) {
        List<Auditorium> auditoriums = auditoriumRepository.findAuditoriumsByTheaterId(id);
        auditoriums.forEach(
                auditorium -> scheduleRepository.deleteAllByAuditoriumId(auditorium.getId())
        );
        theaterRepository.deleteById(id);
    }
}
