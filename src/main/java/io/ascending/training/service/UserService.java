package io.ascending.training.service;

import io.ascending.training.model.Apartment;
import io.ascending.training.model.User;
import io.ascending.training.repository.impl.UserDAOImpl;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import io.ascending.training.repository.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public boolean save(User u){
        return (userDAO.save(u));
    }

    public boolean update(User u){ return (userDAO.update(u));}

    public boolean delete(User u){ return (userDAO.delete(u));}

    public boolean deleteUserByAccount(String userAccount){ return (userDAO.deleteUserByAccount(userAccount));}

    public boolean deleteUserById(long id){return userDAO.deleteUserById(id);}

    public List<User> getUsers(){return userDAO.getUsers();}

    public User getUserByAccount(String userAccount){return userDAO.getUserByAccount(userAccount);}

    public User getUserById(long uid){return userDAO.getUserById(uid);}
}
