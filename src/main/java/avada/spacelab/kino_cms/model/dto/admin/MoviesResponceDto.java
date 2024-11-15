package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Movie}
 */
public record MoviesResponceDto(
        Long id,
        String uaTitle,
        String posterUrl
) implements Serializable {}