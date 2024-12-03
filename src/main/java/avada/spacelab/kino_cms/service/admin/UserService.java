package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    PagedResponse<UserDto> getUserDtoPage(int page, int size);

    UserDto getUserById(long id);

    UserDto getUserByEmail(String email) throws IllegalArgumentException;

    void save(UserDto userDto);

    void deleteUser(long id);

    long getAllUsersAmount();

    void saveNewUser(String email, String password);
}
