package io.ascending.training.repository.interfaces;

import io.ascending.training.model.postgresModel.Apartment;

import java.util.List;

public interface ApartmentDAO {
    boolean save(Apartment apartment);
    boolean update(Apartment apartment);
//    boolean delete(Apartment apartment);
    boolean deleteApartmentByName(String apartName);
    boolean deleteApartmentById(long id);
    List<Apartment> getApartments();
    Apartment getApartmentByName(String apartName);
    Apartment getApartmentById(long id);

}
