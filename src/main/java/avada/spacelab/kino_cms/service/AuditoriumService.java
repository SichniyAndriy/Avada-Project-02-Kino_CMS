package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.mapper.AuditoriumMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumService {
    private AuditoriumRepository auditoriumRepository;

    public AuditoriumService(
            @Autowired AuditoriumRepository auditoriumRepository
    ) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public AuditoriumDto findAuditoriumById(long id) {
        return AuditoriumMapper.INSTANCE.fromEntityToDto(auditoriumRepository.findById(id));
    }
}
