package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import avada.spacelab.kino_cms.model.entity.User;
import avada.spacelab.kino_cms.model.mapper.admin.UserMapper;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.admin.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(
            @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .peek(item -> item.setPassHash(""))
                .map(UserMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
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

    @Override
    public UserDto getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassHash("");
            return UserMapper.INSTANCE.fromEntityToDto(user);
        }
        return UserDto.EMPTY();
    }

    @Override
    public void save(UserDto userDto) {
        User user = UserMapper.INSTANCE.fromDtoToEntity(userDto);
        if (user.getRegistrationDate() == null) {
            user.setRegistrationDate(LocalDate.now());
        }

        String passHash;
        if (user.getId() != null && ( user.getPassHash() == null || user.getPassHash().isEmpty() )) {
            passHash = userRepository.findPassHashById(user.getId());
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            passHash = "{bcrypt}" + encoder.encode(user.getPassHash());
        }
        user.setPassHash(passHash);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public long getAllUsersAmount() {
        return userRepository.count();
    }

}
