package avada.spacelab.kino_cms.model.dto.admin;

import avada.spacelab.kino_cms.model.entity.EditPage.EditPageType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.EditPage}
 */
public record EditPageDto(
        Long id,
        String title,
        String description,
        String bannerUrl,
        String videoUrl,
        EditPageType type,
        SeoBlockDto seoBlock,
        List<EditPagePictureDto> pictures
) implements Serializable {

    public static EditPageDto EMPTY() {
        return new EditPageDto(
                null,
                null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY(),
                Collections.EMPTY_LIST
        );
    }

}