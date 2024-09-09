package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Theater}
 */
public record TheaterDto(
        Long id,
        String title,
        String description,
        String facilities,
        String logoUrl,
        String mainBannerUrl,
        List<AuditoriumDto> auditoriums,
        SeoBlockDto seoBlock
) implements Serializable {
}
