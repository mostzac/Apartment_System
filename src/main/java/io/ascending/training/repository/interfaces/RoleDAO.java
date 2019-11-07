package io.ascending.training.repository.interfaces;

import io.ascending.training.model.Role;

import java.util.List;

public interface RoleDAO {
    boolean save(Role role);
    boolean update(Role role);
    boolean deleteRoleByName(String roleName);
    boolean deleteRoleById(long id);
    Role getRoleByName(String roleName);
    Role getRoleById(long id);
    List<Role> getRoles();
}
