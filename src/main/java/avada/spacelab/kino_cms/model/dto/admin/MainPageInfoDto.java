package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.MainPageInfo}
 */
public record MainPageInfoDto(
        Long id,
        String phoneNumber1,
        String phoneNumber2,
        String seoText,
        SeoBlockDto seoBlock
) implements Serializable {

    public static MainPageInfoDto EMPTY() {
        return new MainPageInfoDto(
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY()
        );
    }

}