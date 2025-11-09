package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    Map<Long, User> users = new HashMap<>();
    public Long generatorId = 0L;
    private final UserMapper mapper = new UserMapper();

    @Override
    public Map<Long, User> getTableUsers() {
        return users;
    }

    @Override
    public User createUser(User user) {
        user.setId(nextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public UserDto getUser(Long id) {
        if (!users.containsKey(id) || id == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден");
        } else {
            User user = users.get(id);
            UserDto userDto = mapper.userDto(user);
            return userDto;
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> usersList = users.values().stream()
                .map(user -> new UserMapper().userDto(user))
                .collect(Collectors.toList());
        return usersList;
    }

    @Override
    public User updateUser(Long id, User newUser) {
        User user = users.get(id);
        if (newUser.getName() != null) {
            user.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        if (!users.containsKey(id) || id == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден");
        } else {
            users.remove(id);
        }
    }

    private Long nextId() {
        return generatorId++;
    }
}
