package io.ascending.training.service.postgres;

import io.ascending.training.model.postgresModel.User;
import io.ascending.training.repository.postgres.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postgresService")
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public boolean save(User u){
        return (userDAO.save(u));
    }

    public boolean update(User u){ return (userDAO.update(u));}

//    public boolean delete(User u){ return (userDAO.delete(u));}

    public boolean deleteUserByAccount(String userAccount){ return (userDAO.deleteUserByAccount(userAccount));}

    public boolean deleteUserById(long id){return userDAO.deleteUserById(id);}

    public List<User> getUsers(){return userDAO.getUsers();}

    public User getUserByAccount(String userAccount){return userDAO.getUserByAccount(userAccount);}

    public User getUserById(long uid){return userDAO.getUserById(uid);}

    public User getUserByCredential(String userAccount,String password){ return userDAO.getUserByCredential(userAccount,password);}
}
