package io.ascending.training.postgres.service;

import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.UserRepository;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.RoleDAO;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("postgresService")
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserRepository userRepository;


    public boolean save(User u){
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("MongoUser"));
        u.setRoles(roles);

        userRepository.save(new MongoUser(u.getAccount(),0));

        return (userDAO.save(u));
    }

    public boolean update(User u){
        String originalAccount = userDAO.getUserById(u.getId()).getAccount();

        MongoUser user = userRepository.findByName(originalAccount).orElse(new MongoUser(u.getAccount(),0));
        user.setName(u.getAccount());
        userRepository.save(user);

        return (userDAO.update(u));}

//    public boolean delete(User u){ return (userDAO.delete(u));}

    public boolean deleteUserByAccount(String userAccount){ return (userDAO.deleteUserByAccount(userAccount));}

    public boolean deleteUserById(long id){return userDAO.deleteUserById(id);}

    public List<User> getUsers(){return userDAO.getUsers();}

    public User getUserByAccount(String userAccount){return userDAO.getUserByAccount(userAccount);}

    public User getUserById(long uid){return userDAO.getUserById(uid);}

    public User getUserByCredential(String userAccount,String password){ return userDAO.getUserByCredential(userAccount,password);}
}
