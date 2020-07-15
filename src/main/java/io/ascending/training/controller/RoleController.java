package io.ascending.training.controller;

import io.ascending.training.model.postgresModel.Role;
import io.ascending.training.service.postgres.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/role", method = RequestMethod.GET, params = {"roleName"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Role getRoleByName(@RequestParam(name = "roleName") String roleName) {
        return roleService.getRoleByName(roleName);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Role getRoleById(@PathVariable(name = "roleId") long roleId) {
        return roleService.getRoleById(roleId);
    }

    @RequestMapping(value = "/role", method = RequestMethod.DELETE, params = {"roleName"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deleteRoleByName(@RequestParam(name = "roleName") String roleName) {
        return roleService.deleteRoleByName(roleName);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deleteRoleById(@PathVariable(name = "roleId") long roleId) {
        return roleService.deleteRoleById(roleId);
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean updateRole(@RequestBody Role role, @PathVariable(name = "roleId") long roleId) {
        role.setId(roleId);
        return roleService.update(role);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

}
