package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Address}
 */
public record AddressDto(
        Long id,
        String city,
        String zipCode,
        String street,
        String houseNumber,
        String flatNumber
) implements Serializable {

    public static AddressDto EMPTY() {
        return new AddressDto(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public String toString() {
        return  city + ", " + street + ", " + houseNumber +
                (flatNumber != null ? ", " + flatNumber : "");
    }
}
