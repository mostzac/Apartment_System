package io.ascending.training.controller.mongo;


import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mongo/users")
public class MongoUserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MongoUser> getUsers() {
        return userService.findAll();
    }
}
