package avada.spacelab.kino_cms.model.dto;

import avada.spacelab.kino_cms.model.entity.User.Gender;
import avada.spacelab.kino_cms.model.entity.User.Language;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.User}
 */
public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String login,
        String phoneNumber,
        String email,
        AddressDto address,
        String passHash,
        String paymentCardNumber,
        Language language,
        Gender sex,
        LocalDate birthDate
) implements Serializable {
}
