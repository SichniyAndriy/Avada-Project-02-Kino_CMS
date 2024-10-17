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
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.AuditoriumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;
    private final TheaterRepository theaterRepository;

    public AuditoriumServiceImpl(
            AuditoriumRepository auditoriumRepository,
            TheaterRepository theaterRepository
    ) {
        this.auditoriumRepository = auditoriumRepository;
        this.theaterRepository = theaterRepository;
    }

    public AuditoriumDto getById(long id) {
        Optional<Auditorium> auditoriumOptional = auditoriumRepository.findById(id);
        if (auditoriumOptional.isPresent()) {
            Auditorium auditorium = auditoriumOptional.get();
            if(auditorium.getSeoBlock() == null) {
                auditorium.setSeoBlock(new SeoBlock());
            }
            return AuditoriumMapper.INSTANCE.fromEntityToDto(auditorium);
        }
        return AuditoriumDto.EMPTY();
    }

    public void deleteAuditoriumById(long audId) {
        auditoriumRepository.deleteById(audId);
    }

    public void save(AuditoriumDto auditoriumDto, String picturesJson) {
        List<AuditoriumPictureDto> pictureDtos;
        try {
            pictureDtos =  new ObjectMapper().
                    readValue(picturesJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<AuditoriumPicture> pictures = pictureDtos.stream()
                .map(AuditoriumPictureMapper.INSTANCE::fromDtoToEntity)
                .toList();

        Auditorium auditorium = AuditoriumMapper.INSTANCE.fromDtoToEntity(auditoriumDto);

        auditorium.setPictures(pictures);
        auditorium.getPictures().forEach(picture -> picture.setAuditorium(auditorium));

        Optional<Theater> theaterOptional = Optional.ofNullable(
                theaterRepository.findTheaterByIdAuditorium(auditorium.getId())
        );
        if (!theaterOptional.isPresent()){

        }
        theaterOptional.ifPresent(auditorium::setTheater);

        auditoriumRepository.save(auditorium);
    }
}
