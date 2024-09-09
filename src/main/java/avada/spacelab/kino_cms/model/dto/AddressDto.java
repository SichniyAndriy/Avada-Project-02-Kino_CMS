package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Address}
 */
public record AddressDto(
        Long id,
        String city,
        String street,
        String houseNumber,
        String flatNumber
) implements Serializable {
}
