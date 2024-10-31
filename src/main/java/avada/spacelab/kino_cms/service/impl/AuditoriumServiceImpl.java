package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.AuditoriumPictureDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.AuditoriumPicture;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.mapper.AuditoriumMapper;
import avada.spacelab.kino_cms.model.mapper.AuditoriumPictureMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.AuditoriumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;
    private final TheaterRepository theaterRepository;
    private final ScheduleRepository scheduleRepository;


    public AuditoriumServiceImpl(
            @Autowired AuditoriumRepository auditoriumRepository,
            @Autowired TheaterRepository theaterRepository,
            @Autowired ScheduleRepository scheduleRepository
    ) {
        this.auditoriumRepository = auditoriumRepository;
        this.theaterRepository = theaterRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /*---------------------------- Public part ----------------------------*/

    public AuditoriumDto getById(long id) {
        Optional<Auditorium> auditoriumOptional = auditoriumRepository.findById(id);
        if (auditoriumOptional.isPresent()) {
            Auditorium auditorium = auditoriumOptional.get();
            if (auditorium.getSeoBlock() == null) {
                auditorium.setSeoBlock(new SeoBlock());
            }
            return AuditoriumMapper.INSTANCE.fromEntityToDto(auditorium);
        }
        return AuditoriumDto.EMPTY();
    }

    public void deleteAuditoriumById(long audId) {
        scheduleRepository.deleteAllByAuditoriumId(audId);
        auditoriumRepository.deleteById(audId);
    }

    public void save(AuditoriumDto auditoriumDto, String picturesJson) {
        Auditorium auditorium = AuditoriumMapper.INSTANCE.fromDtoToEntity(auditoriumDto);
        setPictures(auditorium, picturesJson);
        setTheater(auditorium, auditoriumDto.theaterId());
        setDate(auditorium);
        auditoriumRepository.save(auditorium);
    }

    /*---------------------------- Private part ----------------------------*/

    private void setDate(Auditorium auditorium) {
        if (auditorium.getDate() == null) {
            auditorium.setDate(LocalDate.now());
        }
    }

    private void setPictures(Auditorium auditorium, String picturesJson) {
        List<AuditoriumPicture> pictures = parseJsonToPicturesDto(picturesJson);
        auditorium.setPictures(pictures);
        auditorium.getPictures().forEach(picture -> picture.setAuditorium(auditorium));
    }

    private void setTheater(Auditorium auditorium, long theaterId) {
        Optional<Theater> theaterOptional = theaterRepository.findById(theaterId);
        theaterOptional.ifPresent(auditorium::setTheater);
    }

    private List<AuditoriumPicture> parseJsonToPicturesDto(String picturesJson) {
        List<AuditoriumPictureDto> pictureDtos;
        try {
            pictureDtos = new ObjectMapper().
                    readValue(picturesJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return pictureDtos.stream()
                .map(AuditoriumPictureMapper.INSTANCE::fromDtoToEntity)
                .toList();
    }
}
