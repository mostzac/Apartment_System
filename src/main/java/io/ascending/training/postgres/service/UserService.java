package io.ascending.training.postgres.service;

import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.RoleDAO;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("postgresService")
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    public boolean save(User u){
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("MongoUser"));
        u.setRoles(roles);
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
