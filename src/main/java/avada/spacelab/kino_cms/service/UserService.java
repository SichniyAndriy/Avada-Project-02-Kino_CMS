package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.UserDto;
import avada.spacelab.kino_cms.model.entity.User;
import avada.spacelab.kino_cms.model.mapper.UserMapper;
import avada.spacelab.kino_cms.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(
            @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .peek(item -> item.setPassHash(""))
                .map(UserMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public PagedResponse<UserDto> getUserDtoPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageRequest);
        userPage.forEach(user -> user.setPassHash(""));
        ArrayList<UserDto> collect = userPage.getContent().stream()
                .map(UserMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
       return PagedResponse.Of(
                collect,
                userPage.getSize(),
                userPage.getNumber(),
                userPage.getTotalPages(),
                userPage.getNumberOfElements(),
                userPage.hasPrevious(),
                userPage.hasNext(),
                userPage.isFirst(),
                userPage.isLast()
        );
    }

    public UserDto getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassHash("");
            return UserMapper.INSTANCE.fromEntityToDto(user);
        }
        return null;
    }

    public void save(UserDto userDto) {
        userRepository.save(UserMapper.INSTANCE.fromDtoToEntity(userDto));
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}