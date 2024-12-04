package avada.spacelab.kino_cms.model.dto.admin;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Contact}
 */
public record ContactDto(
        Long id,
        String title,
        String address,
        String coordinates
) implements Serializable {}