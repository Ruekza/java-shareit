package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<Long, User> getTableUsers() {
        return userRepository.getTableUsers();
    }

    @Override
    public User createUser(UserDto userDto) {
        if (isEmailExist(userDto)) {
            throw new ValidationException("Такой email уже используется");
        } else {
            User user = UserMapper.toUser(userDto);
            return userRepository.createUser(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        if (!isUserExist(id)) {
            throw new EntityNotFoundException("Пользователя с указанным id не существует");
        }
        if (userDto.getEmail() != null && isEmailExist(userDto)) {
            throw new ValidationException("Такой email уже используется");
        } else {
            User user = UserMapper.toUser(userDto);
            return userRepository.updateUser(id, user);
        }
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.getUser(id);
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> listOfUsers = userRepository.getAllUsers();
        return listOfUsers.stream()
                .map(user -> UserMapper.toUserDto(user))
                .collect(Collectors.toList());
    }

    private boolean isEmailExist(UserDto userDto) {
        String email = userDto.getEmail();
        return getTableUsers().values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean isUserExist(Long id) {
        return getTableUsers().containsKey(id);
    }
}
