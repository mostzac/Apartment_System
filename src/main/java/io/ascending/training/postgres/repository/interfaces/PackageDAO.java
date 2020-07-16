package io.ascending.training.postgres.repository.interfaces;

import io.ascending.training.postgres.model.Package;

import java.util.List;

public interface PackageDAO {
    boolean save(Package pack);
    boolean update(Package pack);
    //TODO byxxx
    boolean deletePackageByShipNumber(String shipNum);
    boolean deletePackageById(long id);
    List<Package> getPackages();
    Package getPackageByShipNumber(String packShipNum);
    Package getPackageById(long id);

}
