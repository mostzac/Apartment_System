package io.ascending.training.repository.interfaces;

import io.ascending.training.model.User;

import java.util.List;

public interface UserDAO {
    boolean save(User user);
    boolean update(User user);
    boolean delete(User user);
    boolean deleteUserByAccount(String userAccount);
    List<User> getUsers();
    User getUserByAccount(String userAccount);
}
