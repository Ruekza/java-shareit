package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap<>();
    private Long generatorId = 0L;

    @Override
    public Map<Long, User> getTableUsers() {
        return new HashMap<>(users);
    }

    @Override
    public User createUser(User user) {
        user.setId(nextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(Long id) {
        if (!users.containsKey(id) || id == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден");
        } else {
            return users.get(id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
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
