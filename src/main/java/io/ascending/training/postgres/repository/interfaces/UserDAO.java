package io.ascending.training.postgres.repository.interfaces;

import io.ascending.training.postgres.model.User;

import java.util.List;

public interface UserDAO {
    boolean save(User user);
    boolean update(User user);
//    boolean delete(User user);
    boolean deleteUserByAccount(String userAccount);
    boolean deleteUserById(long id);
    List<User> getUsers();
    User getUserByAccount(String userAccount);
    User getUserByCredential(String userAccount,String password);
    User getUserById(long id);
}
