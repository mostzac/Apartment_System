package io.ascending.training.repository.serviceTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.jdbc.UsersDAO;
import io.ascending.training.model.Package;
import io.ascending.training.model.User;
import io.ascending.training.repository.interfaces.PackageDAO;
import io.ascending.training.repository.interfaces.UserDAO;
import io.ascending.training.service.PackageService;
import io.ascending.training.service.UserService;
import org.hibernate.loader.collection.PaddedBatchingCollectionInitializerBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class PackageServiceTest {
    @Autowired
    private PackageService packageService;
    @Autowired
    private UserService userService;
    private Package pack;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init(){
        pack = new Package();
        pack.setShipNumber("test111");
        pack.setShipper("Amazon");
        pack.setDeliveredDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        pack.setStatus(0);
        pack.setArrangeDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        pack.setNotes("testNote");
        User u = userService.getUserByAccount("DaveAccount");
        pack.setUser(u);
    }

    @Test
    public void test(){
        List<Package> packages = packageService.getPackages();
        packages.forEach(pack-> logger.info(pack.toString()));

        Assert.assertTrue(packageService.save(pack));
        Package p = packageService.getPackageByShipNumber(pack.getShipNumber());
        p.setNotes("updated");
        Assert.assertTrue(packageService.update(p));
        p = packageService.getPackageByShipNumber(p.getShipNumber());
        logger.info(p.toString());
        Assert.assertTrue(packageService.delete(p));

    }


}
