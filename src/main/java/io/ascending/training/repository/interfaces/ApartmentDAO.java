package io.ascending.training.repository.interfaces;

import io.ascending.training.jdbc.UsersDAO;
import io.ascending.training.model.Apartment;
import io.ascending.training.model.User;

import java.util.List;
import java.util.Set;

public interface ApartmentDAO {
    boolean save(Apartment apartment);
    boolean update(Apartment apartment);
    boolean delete(Apartment apartment);
    boolean deleteApartmentByName(String apartName);
    boolean deleteApartmentById(long id);
    List<Apartment> getApartments();
    Apartment getApartmentByName(String apartName);
    Apartment getApartmentById(long id);

}
