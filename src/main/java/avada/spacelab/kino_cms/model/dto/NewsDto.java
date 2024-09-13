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
    public NewsDto(
            Long id,
            String title,
            String content,
            LocalDate date,
            String mainPicture,
            SeoBlockDto seoBlock,
            Status status
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.mainPicture = mainPicture;
        this.seoBlock = seoBlock;
        this.status = status;
    }

    public static NewsDto EMPTY() {
        return new NewsDto(null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY(),
                null);
    }
}
