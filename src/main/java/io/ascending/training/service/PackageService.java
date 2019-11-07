package io.ascending.training.service;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import io.ascending.training.model.Package;
import io.ascending.training.repository.interfaces.PackageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageDAO packageDAO;

    public boolean save(Package pack){return packageDAO.save(pack);};
    public boolean update(Package pack){return packageDAO.update(pack);};
    public boolean deletePackageByShipNumber(String shipNum){return packageDAO.deletePackageByShipNumber(shipNum);};
    public boolean deletePackageById(long id){return packageDAO.deletePackageById(id);}
    public List<Package> getPackages(){return packageDAO.getPackages();};
    public Package getPackageByShipNumber(String packShipNum){return packageDAO.getPackageByShipNumber(packShipNum);};
    public Package getPackageById(long packId){return packageDAO.getPackageById(packId);}

}