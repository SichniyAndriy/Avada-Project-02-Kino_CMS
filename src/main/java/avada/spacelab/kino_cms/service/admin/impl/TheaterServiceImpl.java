package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.model.dto.admin.TheaterPictureDto;
import avada.spacelab.kino_cms.model.dto.user.TheaterResponceDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import avada.spacelab.kino_cms.model.mapper.admin.TheaterMapper;
import avada.spacelab.kino_cms.model.mapper.admin.TheaterPictureMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.admin.TheaterService;
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
public class TheaterServiceImpl implements TheaterService {

    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final ScheduleRepository scheduleRepository;


    public TheaterServiceImpl(
            @Autowired TheaterRepository theaterRepository,
            @Autowired AuditoriumRepository auditoriumRepository,
            @Autowired ScheduleRepository scheduleRepository
    ) {
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /*----------------------------- PUBLIC PART -----------------------------*/

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Theater::getId))
                .map(TheaterMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<TheaterResponceDto> getAllTheaterResponceDtos() {
        List<String> strings = theaterRepository.findTheaterResponceDtos();
        return strings.stream().map(s -> {
                    String[] row = s.split(";");
                    Long id = Long.parseLong(row[0]);
                    String title = !row[1].equals("empty") ? row[1] : null;
                    String logoUrl = !row[2].equals("empty") ? row[2] : null;
                    return new TheaterResponceDto(id, title, logoUrl);
                })
                .sorted(Comparator.comparing(TheaterResponceDto::id))
                .toList();
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
        Theater theater = TheaterMapper.INSTANCE.fromDtoToEntity(theaterDto);
        if (theater.getSeoBlock() == null) {
            theater.setSeoBlock(new SeoBlock());
        }
        setPictures(theater, picturesJson);
        setAuditoriums(theater);

        Theater savedTheater = theaterRepository.save(theater);
        return TheaterMapper.INSTANCE.fromEntityToDto(savedTheater);
    }

    public void deleteById(long id) {
        List<Auditorium> auditoriums = auditoriumRepository.findAuditoriumsByTheaterId(id);
        auditoriums.forEach(
                auditorium -> scheduleRepository.deleteAllByAuditoriumId(auditorium.getId())
        );
        theaterRepository.deleteById(id);
    }

    /*----------------------------- PRIVATE PART -----------------------------*/

    private void setPictures(Theater theater, String picturesJson) {
        List<TheaterPicture> pictures =
                parseJsonToTheaterPictures(picturesJson);
        theater.setPictures(pictures);
        pictures.forEach(picture -> picture.setTheater(theater));
    }

    private void setAuditoriums(Theater theater) {
        List<Auditorium> auditoriums =
                auditoriumRepository.findAuditoriumsByTheaterId(theater.getId());
        theater.setAuditoriums(auditoriums);
        auditoriums.forEach(auditorium -> auditorium.setTheater(theater));
    }

    private List<TheaterPicture> parseJsonToTheaterPictures(String picturesJson) {
        List<TheaterPictureDto> theaterPictureDtos;
        try {
            theaterPictureDtos = new ObjectMapper()
                    .readValue(picturesJson, new TypeReference<>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return theaterPictureDtos.stream()
                .map(TheaterPictureMapper.INSTANCE::fromDtoToEntity)
                .toList();
    }

}
