package io.ascending.training.controller;

import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.model.postgresModel.Package;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.service.ApartmentService;
import io.ascending.training.service.PackageService;
import io.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/test"})
public class TestController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String testDefault() {
        return "You have connected to the server!";
    }

    @RequestMapping(value = "/paths", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map helloWorld() {
        Map<String, String> m = new HashMap();
        m.put("result", "HelloWorld");
        return m;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers() {
        List<User> users = userService.getUsers();
        return users;
    }

    @RequestMapping(value = "/packages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Package> getPackages() {
        return packageService.getPackages();
    }

    /**
     * url: /test/path/2/3
     * method: GET
     *
     * @param p1
     * @param p2
     * @return
     */
    @RequestMapping(value = "/path/{pathValue1}/object/{pathValue2}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getPath(@PathVariable(name = "pathValue1") String p1, @PathVariable(name = "pathValue2") String p2) {
        return p1 + "," + p2;
    }

    @RequestMapping(value = "/mongo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MongoUser usertoString() {
        return new MongoUser("a",10);
    }
}
