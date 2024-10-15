package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.dto.TheaterPictureDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import avada.spacelab.kino_cms.model.mapper.TheaterMapper;
import avada.spacelab.kino_cms.model.mapper.TheaterPictureMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;

    public TheaterService(
            @Autowired TheaterRepository theaterRepository,
            AuditoriumRepository auditoriumRepository) {
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
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

    public void save(TheaterDto theaterDto, String picturesJson) {
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
        if (theater.getId() == 0) {
            theater.setId(null);
        }

        theater.setPictures(pictures);
        pictures.forEach(picture -> picture.setTheater(theater));

        List<Auditorium> auditoriums = auditoriumRepository.findAuditoriumsByTheaterId(theater.getId());
        theater.setAuditoriums(auditoriums);
        auditoriums.forEach(auditorium -> auditorium.setTheater(theater));

        theaterRepository.save(theater);
    }
}
