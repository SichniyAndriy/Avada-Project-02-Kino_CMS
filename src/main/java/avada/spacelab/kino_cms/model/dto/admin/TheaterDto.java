package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Theater}
 */
public record TheaterDto(
        Long id,
        String title,
        String description,
        String conditions,
        String logoUrl,
        String bannerUrl,
        List<TheaterPictureDto> pictures,
        List<AuditoriumDto> auditoriums,
        SeoBlockDto seoBlock
) implements Serializable {

    public static TheaterDto EMPTY() {
        return new TheaterDto(
                null,
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                SeoBlockDto.EMPTY());
    }

}
