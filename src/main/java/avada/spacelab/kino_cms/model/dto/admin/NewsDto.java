package avada.spacelab.kino_cms.model.dto.admin;

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
        String pictureUrl,
        String trailerUrl,
        SeoBlockDto seoBlock,
        Status status
) implements Serializable {

    public static NewsDto EMPTY() {
        return new NewsDto(null,
                null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY(),
                null
        );
    }
}
