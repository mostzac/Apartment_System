package io.ascending.training.repository;

import io.ascending.training.repository.daoTest.ApartmentDAOTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApartmentDAOTest.class
})
public class TestAll {
}
