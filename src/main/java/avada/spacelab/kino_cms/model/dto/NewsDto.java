package avada.spacelab.kino_cms.model.dto;

import avada.spacelab.kino_cms.model.entity.Status;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.News}
 */
public record NewsDto(
        Long id,
        String title,
        String content,
        LocalDate date,
        String mainPicture,
        SeoBlockDto seoBlock,
        Status status
) implements Serializable {
}
