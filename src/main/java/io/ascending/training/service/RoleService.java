package io.ascending.training.service;

import io.ascending.training.model.Role;
import io.ascending.training.repository.interfaces.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;

    public boolean save(Role role){return roleDAO.save(role);}
    public boolean update(Role role){return roleDAO.update(role);}
    public boolean delete(Role role){return roleDAO.delete(role);}
    public boolean deleteById(long id){return roleDAO.deleteById(id);}
    public Role getRoleByName(String roleName){return roleDAO.getRoleByName(roleName);}
}
