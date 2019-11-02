package io.ascending.training.controller;

import io.ascending.training.model.Apartment;
import io.ascending.training.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = {"/apartments","/apts"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Apartment> getApartments(){
        return apartmentService.getApartments();
    }
}
