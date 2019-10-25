package io.ascending.training.repository.interfaces;

import io.ascending.training.model.Package;

import java.util.List;

public interface PackageDAO {
    boolean save(Package pack);
    boolean update(Package pack);
    //TODO byxxx
    boolean delete(Package pack);
    List<Package> getPackages();
    Package getPackageByShipNumber(String packShipNum);

}
