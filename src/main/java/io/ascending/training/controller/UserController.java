package io.ascending.training.controller;

import io.ascending.training.jdbc.UsersDAO;
import io.ascending.training.model.Apartment;
import io.ascending.training.model.User;
import io.ascending.training.service.ApartmentService;
import io.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "/users", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/user/{account}", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByAccountPath(@PathVariable(name = "account")String p1){
        return userService.getUserByAccount(p1);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET,params={"Account"},produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByAccountParam(@RequestParam(name = "Account")String p1){
        return userService.getUserByAccount(p1);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET,params ={"Id"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByIdParam(@RequestParam(name = "Id")long p1){
        return userService.getUserById(p1);
    }

    @RequestMapping(value = "/user/{aptName}",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createUser(@RequestBody User user,@PathVariable(name = "aptName") String aptName){
        Apartment apartment = apartmentService.getApartmentByName(aptName);
        user.setApartment(apartment);
        return userService.save(user);
    }

    @RequestMapping(value = "/user",method = RequestMethod.DELETE,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean delelteUser(@RequestParam(name = "Id") long id){
        return userService.deleteUserById(id);
    }

}
