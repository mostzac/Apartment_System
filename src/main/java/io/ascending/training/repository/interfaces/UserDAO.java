package io.ascending.training.repository.interfaces;

import io.ascending.training.model.User;

import java.util.List;

public interface UserDAO {
    boolean save(User user);
    boolean update(User user);
    boolean delete(User user);
    boolean deleteUserByName(String userName);
    List<User> getUsers();
    User getUserByName(String userName);
}
