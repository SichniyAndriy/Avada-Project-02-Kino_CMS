package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    PagedResponse<UserDto> getUserDtoPage(int page, int size);

    UserDto getUserById(long id);

    void save(UserDto userDto);

    void deleteUser(long id);

    long getAllUsersAmount();

}
