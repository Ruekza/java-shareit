package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    public Map<Long, User> getTableUsers();

    public User createUser(User user);

    public UserDto getUser(Long id);

    public List<UserDto> getAllUsers();

    public User updateUser(Long id, User newUser);

    public void deleteUser(Long id);
}
