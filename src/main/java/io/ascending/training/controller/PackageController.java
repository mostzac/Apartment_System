package io.ascending.training.controller;

import io.ascending.training.model.Package;
import io.ascending.training.model.User;
import io.ascending.training.service.PackageService;
import io.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/packs")
public class PackageController {
    @Autowired
    private PackageService packageService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Package> getPackages(){
        return packageService.getPackages();
    }

    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Package getPackageById(@PathVariable(name = "packId") long id) {
        return packageService.getPackageById(id);
    }

    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deletePackageById(@PathVariable(name = "packId") long id) {
        return packageService.deletePackageById(id);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.GET, params = "shipNum", produces = MediaType.APPLICATION_JSON_VALUE)
    public Package getPackageByShipNumber(@RequestParam(name = "shipNum") String shipNum) {
        return packageService.getPackageByShipNumber(shipNum);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.DELETE, params = "shipNum", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deletePackageByShipNumber(@RequestParam(name = "shipNum") String shipNum) {
        return packageService.deletePackageByShipNumber(shipNum);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.POST,params = "userAccount",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createPackage(@RequestBody Package pack,@RequestParam(name = "userAccount")String userAccount) {
        User user = userService.getUserByAccount(userAccount);
        pack.setUser(user);
        return packageService.save(pack);
    }

    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean updatePackage(@RequestBody Package pack, @PathVariable(name = "packId") long id) {
        pack.setId(id);
        return packageService.update(pack);
    }
}
