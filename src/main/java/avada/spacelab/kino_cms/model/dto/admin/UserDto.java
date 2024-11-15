package avada.spacelab.kino_cms.model.dto.admin;

import avada.spacelab.kino_cms.model.entity.User.Gender;
import avada.spacelab.kino_cms.model.entity.User.Language;
import avada.spacelab.kino_cms.model.entity.User.Role;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.User}
 */
public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String nickName,
        String phone,
        String email,
        AddressDto address,
        String passHash,
        String cardNumber,
        Language language,
        Gender gender,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate registrationDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        Role role
) implements Serializable {

    public static UserDto EMPTY() {
        return new UserDto(
                null,
                null,
                null,
                null,
                null,
                null,
                AddressDto.EMPTY(),
                null,
                null,
                null,
                null,
                null,
                null,
                Role.USER
        );
    }
}
