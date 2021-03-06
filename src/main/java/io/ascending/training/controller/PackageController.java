package io.ascending.training.controller;

import io.ascending.training.postgres.model.Package;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.PackageService;
import io.ascending.training.postgres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/packs")
public class PackageController {
    @Autowired
    private PackageService packageService;
    @Autowired
    @Qualifier("postgresService")
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Package> getPackages(){
        return packageService.getPackages();
    }

    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Package getPackageById(@PathVariable(name = "packId") long id) {
        return packageService.getPackageById(id);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.GET, params = "shipNum", produces = MediaType.APPLICATION_JSON_VALUE)
    public Package getPackageByShipNumber(@RequestParam(name = "shipNum") String shipNum) {
        return packageService.getPackageByShipNumber(shipNum);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.POST,params = "userAccount",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createPackage(@RequestBody Package pack,@RequestParam(name = "userAccount")String userAccount) {
        User user = userService.getUserByAccount(userAccount);
        pack.setUser(user);
        return packageService.save(pack);
    }

    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean updatePackage(@RequestBody Package pack, @PathVariable(name = "packId") long id, @RequestParam Optional<String> userAccount) {
        try {
            pack.setId(id);
            if (userAccount.isPresent()) {
                pack.setUser(userService.getUserByAccount(userAccount.get()));
            } else {
                pack.setUser(packageService.getPackageById(id).getUser());
            }
            return packageService.update(pack);
        } catch (NullPointerException e) {
            return false;
        }
    }


    @RequestMapping(value = "/pack/{packId}", method = RequestMethod.DELETE)
    public Boolean deletePackageById(@PathVariable(name = "packId") long id) {
        return packageService.deletePackageById(id);
    }

    @RequestMapping(value = "/pack", method = RequestMethod.DELETE, params = "shipNum")
    public Boolean deletePackageByShipNumber(@RequestParam(name = "shipNum") String shipNum) {
        return packageService.deletePackageByShipNumber(shipNum);
    }
}
