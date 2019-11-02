package io.ascending.training.service;

import io.ascending.training.model.Apartment;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentDAO apartmentDAO;

    public boolean save(Apartment apt){
        return (apartmentDAO.save(apt));
    }

    public boolean update(Apartment u){ return (apartmentDAO.update(u));}

    public boolean delete(Apartment u){ return (apartmentDAO.delete(u));}

    public boolean deleteApartmentByName(String apartName){ return (apartmentDAO.deleteApartmentByName(apartName));}

    public List<Apartment> getApartments(){return apartmentDAO.getApartments();}

    public Apartment getApartmentByName(String apartName){return apartmentDAO.getApartmentByName(apartName);}

    public Apartment getApartmentById(long aptId){return apartmentDAO.getApartmentById(aptId);}
}
