package io.ascending.training.repository.interfaces;

import io.ascending.training.model.Role;

public interface RoleDAO {
    boolean save(Role role);
    boolean update(Role role);
    boolean delete(Role role);
    boolean deleteById(long id);
    Role getRoleByName(String roleName);
}
