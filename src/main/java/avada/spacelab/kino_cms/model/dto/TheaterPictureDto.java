package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.TheaterPicture}
 */
public record TheaterPictureDto(Long id, String path) implements Serializable {
}