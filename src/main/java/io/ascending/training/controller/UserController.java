package io.ascending.training.controller;

import io.ascending.training.jdbc.UsersDAO;
import io.ascending.training.model.User;
import io.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
