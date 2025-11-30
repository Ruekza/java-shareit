package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (isEmailExist(userDto)) {
            throw new ValidationException("Такой email уже используется");
        } else {
            User user = UserMapper.toUser(userDto);
            User createdUser = userRepository.save(user);
            return UserMapper.toUserDto(createdUser);
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (!isUserExist(id)) {
            throw new EntityNotFoundException("Пользователя с указанным id не существует");
        } else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователя с указанным id не существует"));
        if (userDto.getName() != null) {
            oldUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !isEmailExist(userDto)) {
            oldUser.setEmail(userDto.getEmail());
        } else if (userDto.getEmail() != null && isEmailExist(userDto)) {
            throw new ConflictException("Такой email уже используется");
        }
        User updatedUser = userRepository.save(oldUser);
        return UserMapper.toUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserMapper.toUserDto(user);
        } else {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> listOfUsers = userRepository.findAll();
        return listOfUsers.stream()
                .map(user -> UserMapper.toUserDto(user))
                .collect(Collectors.toList());
    }

    private boolean isEmailExist(UserDto userDto) {
        String email = userDto.getEmail();
        return getAllUsers().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean isUserExist(Long id) {
        return userRepository.existsById(id);
    }
}
