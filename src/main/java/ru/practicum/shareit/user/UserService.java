package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    boolean isUserExist(Long id);
}
