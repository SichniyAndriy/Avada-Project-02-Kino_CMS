package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.UserDto;
import avada.spacelab.kino_cms.model.entity.User;
import avada.spacelab.kino_cms.model.mapper.UserMapper;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final int SIZE = 3;
    private final long ID = 1;


    @TestFactory
    @DisplayName("Test getAllUsers()")
    List<DynamicTest> test_getAllUsers() {
        return List.of(
                dynamicTest(
                        "With existing list",
                        () -> {
                            List<User> users = LongStream.rangeClosed(1, SIZE)
                                    .mapToObj(this::getUser)
                                    .toList();

                            when(userRepository.findAll()).thenReturn(users);

                            List<UserDto> result = userService.getAllUsers();

                            Assertions.assertAll(
                                    () -> assertEquals(SIZE, result.size()),
                                    () -> assertEquals(1L, result.getFirst().id()),
                                    () -> assertEquals(3, result.getLast().id())
                            );
                            verify(userRepository, times(1)).findAll();
                        }
                ),
                dynamicTest(
                        "With empty list",
                        () -> {
                            when(userRepository.findAll()).thenReturn(Collections.emptyList());

                            List<UserDto> result = userService.getAllUsers();

                            assertEquals(0, result.size());
                            verify(userRepository, times(2)).findAll();
                        }
                ),
                dynamicTest(
                        "With null list",
                        () -> {
                            when(userRepository.findAll()).thenReturn(null);

                            assertThrows(
                                    NullPointerException.class,
                                    () -> userService.getAllUsers()
                            );
                            verify(userRepository, times(3)).findAll();
                        }
                )
        );
    }

    @Test
    @DisplayName("Test getUserDtoPage()")
    void test_getUserDtoPage() {
        Page<User> page = new PageImpl<>(List.of(new User(), new User(), new User()));
        when(userRepository.findAll(any(PageRequest.class)))
                .thenReturn(page);

        PagedResponse<UserDto> userDtoPage = userService.getUserDtoPage(1, 3);

        assertEquals(SIZE, userDtoPage.content().size());
        verify(userRepository, times(1)).findAll(any(PageRequest.class));
    }

    @TestFactory
    @DisplayName("Test getUserById()")
    List<DynamicTest> test_getUserById() {
        return List.of(
                dynamicTest(
                        "With valid id",
                        () -> {
                            when(userRepository.findById(1L)).thenReturn(Optional.of(getUser(1L)));

                            UserDto result = userService.getUserById(1L);

                            assertEquals(1L, result.id());
                            verify(userRepository, times(1)).findById(1L);
                        }
                ),
                dynamicTest(
                        "With invalid id",
                        () -> {
                            when(userRepository.findById(0L)).thenReturn(Optional.empty());

                            UserDto result = userService.getUserById(0);

                            assertNull(result.id());
                            verify(userRepository, times(1)).findById(0L);
                        }
                )
        );
    }

    @TestFactory
    @DisplayName("Test save()")
    List<DynamicTest> save() {
        final String PASSWORD_HASH = "{bcrypt}2A";
        final String PASSWORD = "password";
        return List.of(
                dynamicTest(
                        "Valid parameter with date",
                        () -> {
                            User user = new User();
                            user.setId(1L);
                            user.setRegistrationDate(LocalDate.now());
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.findPassHashById(anyLong())).thenReturn(PASSWORD_HASH);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(1)).findPassHashById(anyLong());
                            verify(userRepository, times(1)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter without date",
                        () -> {
                            User user = new User();
                            user.setId(1L);
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.findPassHashById(anyLong())).thenReturn(PASSWORD_HASH);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(2)).findPassHashById(anyLong());
                            verify(userRepository, times(2)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter with null id and null passHash",
                        () -> {
                            User user = new User();
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);

                            assertThrows(
                                    IllegalArgumentException.class,
                                    () -> userService.save(userDto)
                            );
                            verify(userRepository, times(2)).findPassHashById(anyLong());
                            verify(userRepository, times(2)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter with null id and empty passHash",
                        () -> {
                            User user = new User();
                            user.setPassHash("");
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(2)).findPassHashById(anyLong());
                            verify(userRepository, times(3)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter with null id and not empty passHash",
                        () -> {
                            User user = new User();
                            user.setPassHash(PASSWORD_HASH);
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(2)).findPassHashById(anyLong());
                            verify(userRepository, times(4)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter with not null id and empty passHash",
                        () -> {
                            User user = new User();
                            user.setId(ID);
                            user.setPassHash("");
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.findPassHashById(anyLong())).thenReturn(PASSWORD_HASH);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(3)).findPassHashById(anyLong());
                            verify(userRepository, times(5)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Valid parameter with not null id and not empty passHash",
                        () -> {
                            User user = new User();
                            user.setId(ID);
                            user.setPassHash(PASSWORD);
                            UserDto userDto = UserMapper.INSTANCE.fromEntityToDto(user);
                            when(userRepository.save(any(User.class))).thenReturn(user);

                            userService.save(userDto);

                            verify(userRepository, times(3)).findPassHashById(anyLong());
                            verify(userRepository, times(6)).save(any(User.class));
                        }
                ),
                dynamicTest(
                        "Invalid parameter",
                        () -> {
                            assertThrows(
                                    NullPointerException.class,
                                    () -> userService.save(null)
                            );
                            verify(userRepository, never()).save(isNull());
                        }
                )
        );
    }

    @Test
    @DisplayName("Test deleteUser()")
    void deleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test getAllUsersAmount()")
    void getAllUsersAmount() {
        when(userRepository.count()).thenReturn(3L);
        long allUsersAmount = userService.getAllUsersAmount();
        assertEquals(SIZE, allUsersAmount);
        verify(userRepository, times(1)).count();
    }

    private User getUser(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

}
