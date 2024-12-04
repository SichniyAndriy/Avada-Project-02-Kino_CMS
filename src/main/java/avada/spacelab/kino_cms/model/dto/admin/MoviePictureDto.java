package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.MoviePicture}
 */
public record MoviePictureDto(
        Long id,
        String path
) implements Serializable {}
