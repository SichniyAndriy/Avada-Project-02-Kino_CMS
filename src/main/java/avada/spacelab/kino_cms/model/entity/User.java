package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String lastName;

    @Column(name = "nick_name", unique = true, nullable = false)
    private String nickName;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name="email", nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "pass_hash", nullable = false, unique = true)
    private String passHash;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public enum Language {
        UKRAINIAN,
        ENGLISH,
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Role {
        GUEST(Set.of(
                Permission.READ_IN_PUBLIC_PART
        )),
        USER(Set.of(
                Permission.READ_IN_PUBLIC_PART,
                Permission.READ_IN_USER_PART,
                Permission.WRITE_IN_USER_PART
        )),
        SERVANT(Set.of(
                Permission.READ_IN_PUBLIC_PART,
                Permission.READ_IN_USER_PART,
                Permission.WRITE_IN_USER_PART,
                Permission.READ_IN_ADMIN_PART
        )),
        ADMIN(Set.of(
                Permission.READ_IN_PUBLIC_PART,
                Permission.READ_IN_USER_PART,
                Permission.WRITE_IN_USER_PART,
                Permission.READ_IN_ADMIN_PART,
                Permission.WRITE_IN_ADMIN_PART
        ));

        private final Set<Permission> permissions;

        Role(Set<Permission> permissions) {
            this.permissions = permissions;
        }

        public List<String> getPermissions(Role role) {
            return role.permissions.stream()
                    .map(Enum::name).toList();
        }
    }

    public enum Permission {
        READ_IN_PUBLIC_PART,
        READ_IN_USER_PART,
        WRITE_IN_USER_PART,
        READ_IN_ADMIN_PART,
        WRITE_IN_ADMIN_PART,
    }

}
