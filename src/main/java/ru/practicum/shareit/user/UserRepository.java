package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    public User createUser(User user);

    public User getUser(Long id);

    public List<User> getAllUsers();

    public User updateUser(Long id, User newUser);

    public void deleteUser(Long id);
}
