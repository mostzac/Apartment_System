package io.ascending.training.controller;

import io.ascending.training.model.Package;
import io.ascending.training.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "/packages", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Package> getPackages(){
        return packageService.getPackages();
    }
}
