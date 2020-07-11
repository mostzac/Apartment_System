package io.ascending.training.controller;

import io.ascending.training.model.postgresModel.Apartment;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.service.ApartmentService;
import io.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET,params ={"Account"},produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByAccount(@RequestParam(name = "Account")String p1){
        return userService.getUserByAccount(p1);
    }

    @RequestMapping(value = "/user/{uid}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserById(@PathVariable(name = "uid")long id){
        return userService.getUserById(id);
    }


    @RequestMapping(value = "/user", method = RequestMethod.DELETE,params ={"Account"},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deleteUserByAccount(@RequestParam(name = "Account")String p1){
        return userService.deleteUserByAccount(p1);
    }

    @RequestMapping(value = "/user/{uid}",method = RequestMethod.DELETE,consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deleteUserById(@PathVariable(name = "uid")long id){
        return userService.deleteUserById(id);
    }


    @RequestMapping(value = "/user",method = RequestMethod.POST,params = {"aptName"},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createUser(@RequestBody User user,@RequestParam(name = "aptName") String aptName){
        Apartment apartment = apartmentService.getApartmentByName(aptName);
        user.setApartment(apartment);
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{uid}",method = RequestMethod.PUT,consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean updateUser(@RequestBody User user,@PathVariable(name = "uid")long id){
        user.setId(id);
        return userService.update(user);
    }

}
