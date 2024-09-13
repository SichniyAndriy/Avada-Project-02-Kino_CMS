package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Auditorium}
 */
public record AuditoriumDto(
        Long id,
        Integer number,
        String schemeUrl,
        String description,
        String mainBannerUrl,
        SeoBlockDto seoBlock
) implements Serializable {
    public AuditoriumDto(
            Long id,
            Integer number,
            String schemeUrl,
            String description,
            String mainBannerUrl,
            SeoBlockDto seoBlock
    ) {
        this.id = id;
        this.number = number;
        this.schemeUrl = schemeUrl;
        this.description = description;
        this.mainBannerUrl = mainBannerUrl;
        this.seoBlock = seoBlock;
    }

    public static AuditoriumDto EMPTY() {
        return new AuditoriumDto(
                null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY()
        );
    }
}
