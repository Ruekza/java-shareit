package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<Long, User> getTableUsers();

    User createUser(User user);

    void deleteUser(Long id);

    User updateUser(Long id, User newUser);

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    boolean isUserExist(Long id);
}
