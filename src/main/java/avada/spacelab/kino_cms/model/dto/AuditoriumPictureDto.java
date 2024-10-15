package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.AuditoriumPicture}
 */
public record AuditoriumPictureDto(Long id, String path) implements Serializable {
}