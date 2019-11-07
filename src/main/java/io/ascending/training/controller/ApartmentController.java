package io.ascending.training.controller;

import io.ascending.training.model.Apartment;
import io.ascending.training.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/apts")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Apartment> getApartments() {
        return apartmentService.getApartments();
    }

    @RequestMapping(value = "/apt/{aptId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Apartment getApartmentById(@PathVariable(name = "aptId") long id) {
        return apartmentService.getApartmentById(id);
    }

    @RequestMapping(value = "/apt/{aptId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean deleteApartmentById(@PathVariable(name = "aptId") long id) {
        return apartmentService.deleteApartmentById(id);
    }

    @RequestMapping(value = "/apt", method = RequestMethod.GET, params = "aptName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Apartment getApartmentByName(@RequestParam(name = "aptName") String aptName) {
        return apartmentService.getApartmentByName(aptName);
    }

    @RequestMapping(value = "/apt", method = RequestMethod.DELETE, params = "aptName", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteApartmentByName(@RequestParam(name = "aptName") String aptName) {
        return apartmentService.deleteApartmentByName(aptName);
    }

    @RequestMapping(value = "/apt", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean createApartment(@RequestBody Apartment apt) {
        return apartmentService.save(apt);
    }

    @RequestMapping(value = "/apt/{aptId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean updateApartment(@RequestBody Apartment apt, @PathVariable(name = "aptId") long id) {
        apt.setId(id);
        return apartmentService.update(apt);
    }


}
