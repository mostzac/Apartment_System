package io.ascending.training.repository.interfaces;

import io.ascending.training.model.Resident;

import java.util.List;

public interface ResidentDAO {
    boolean save(Resident resident);
    boolean update(Resident resident);
    //TODO byxxx
    boolean delete(Resident resident);
    boolean deleteResidentByName(String residentName);
    List<Resident> getResidents();
    Resident getResidentByName(String residentName);
}
