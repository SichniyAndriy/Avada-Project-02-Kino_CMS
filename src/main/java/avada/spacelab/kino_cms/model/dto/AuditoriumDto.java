package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Auditorium}
 */
public record AuditoriumDto(
        Long id,
        Integer number,
        String description,
        LocalDate date,
        String schemeUrl,
        String bannerUrl,
        List<AuditoriumPictureDto> pictures,
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
                Collections.EMPTY_LIST,
                SeoBlockDto.EMPTY()
        );
    }
}
