package ru.practicum.shareit.user;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    public Map<Long, User> getTableUsers();

    public User createUser(User user);

    public User getUser(Long id);

    public List<User> getAllUsers();

    public User updateUser(Long id, User newUser);

    public void deleteUser(Long id);
}
