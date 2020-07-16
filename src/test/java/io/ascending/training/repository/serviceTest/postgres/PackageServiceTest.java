package io.ascending.training.repository.serviceTest.postgres;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Package;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.PackageService;
import io.ascending.training.postgres.service.UserService;
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
        Assert.assertTrue(packageService.deletePackageByShipNumber(p.getShipNumber()));

    }

    @Test
    public void TestId(){
        packageService.save(pack);
        pack = packageService.getPackageById(1);
        pack.toString();
        pack = packageService.getPackageByShipNumber("test111");
        Package apt = packageService.getPackageById(pack.getId());
        if(apt.getId()==pack.getId())
            packageService.deletePackageById(apt.getId());

    }


}
