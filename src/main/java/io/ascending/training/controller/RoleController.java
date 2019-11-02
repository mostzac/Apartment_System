package io.ascending.training.controller;

import io.ascending.training.model.Role;
import io.ascending.training.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/role/{roleName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Role getRoleByName(@PathVariable(name = "roleName")String roleName){
        return roleService.getRoleByName(roleName);
    }

}
