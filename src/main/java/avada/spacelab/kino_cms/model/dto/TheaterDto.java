package avada.spacelab.kino_cms.model.dto;

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
        String facilities,
        String logoUrl,
        String mainBannerUrl,
        List<AuditoriumDto> auditoriums,
        SeoBlockDto seoBlock
) implements Serializable {
    public TheaterDto(
            Long id,
            String title,
            String description,
            String facilities,
            String logoUrl,
            String mainBannerUrl,
            List<AuditoriumDto> auditoriums,
            SeoBlockDto seoBlock
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.facilities = facilities;
        this.logoUrl = logoUrl;
        this.mainBannerUrl = mainBannerUrl;
        this.auditoriums = auditoriums;
        this.seoBlock = seoBlock;
    }

    public static TheaterDto EMPTY() {
        return new TheaterDto(
                null,
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(),
                SeoBlockDto.EMPTY());
    }
}
