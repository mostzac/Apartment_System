package io.ascending.training.service;

import io.ascending.training.model.Role;
import io.ascending.training.repository.interfaces.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;

    public boolean save(Role role){return roleDAO.save(role);}
    public boolean update(Role role){return roleDAO.update(role);}
    public boolean deleteRoleByName(String roleName){return roleDAO.deleteRoleByName(roleName);}
    public boolean deleteRoleById(long id){return roleDAO.deleteRoleById(id);}
    public Role getRoleByName(String roleName){return roleDAO.getRoleByName(roleName);}
    public Role getRoleById(long id){return roleDAO.getRoleById(id);}
    public List<Role> getRoles(){return roleDAO.getRoles();}
}
