package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Map;

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
    public User createUser(User user) {
        if (isEmailExist(user)) {
            throw new ValidationException("Такой email уже используется");
        } else {
            return userRepository.createUser(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public User updateUser(Long id, User newUser) {
        if (!isUserExist(id)) {
            throw new EntityNotFoundException("Пользователя с указанным id не существует");
        }
        if (newUser.getEmail() != null && isEmailExist(newUser)) {
            throw new ValidationException("Такой email уже используется");
        } else {
            return userRepository.updateUser(id, newUser);
        }
    }

    @Override
    public UserDto getUser(Long id) {
        return userRepository.getUser(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers();
    }

    private boolean isEmailExist(User newUser) {
        String email = newUser.getEmail();
        return getTableUsers().values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean isUserExist(Long id) {
        return getTableUsers().containsKey(id);
    }
}
