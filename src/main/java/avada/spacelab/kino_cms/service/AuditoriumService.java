package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.mapper.AuditoriumMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;

    public AuditoriumService(
            @Autowired AuditoriumRepository auditoriumRepository
    ) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public AuditoriumDto findAuditoriumById(long id) {
        Optional<Auditorium> auditorium = auditoriumRepository.findById(id);
        return  auditorium.isPresent() ?
                AuditoriumMapper.INSTANCE.fromEntityToDto(auditoriumRepository.findById(id).get()) :
                AuditoriumDto.EMPTY();
    }

    public void deleteAuditoriumById(long audId) {
        auditoriumRepository.deleteById(audId);
    }
}
