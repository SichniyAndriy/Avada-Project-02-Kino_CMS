package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Auditorium}
 */
public record AuditoriumDto(
        Long id,
        Integer number,
        String description,
        LocalDate date,
        String schemeUrl,
        String mainBannerUrl,
        SeoBlockDto seoBlock
) implements Serializable {

    public static AuditoriumDto EMPTY() {
        return new AuditoriumDto(
                null,
                null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY()
        );
    }
}
