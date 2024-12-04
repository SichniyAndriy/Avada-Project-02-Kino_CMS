package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.SeoBlock}
 */
public record SeoBlockDto(
        Long id,
        String url,
        String title,
        String keywords,
        String description
) implements Serializable {

    public static SeoBlockDto EMPTY() {
        return new SeoBlockDto(null, null, null, null, null);
    }

}
