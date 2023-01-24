package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BaseServiceServiceIMPLTest {

    @Autowired
    private BaseServiceServiceIMPL baseServiceServiceIMPL;


    @Test
    @Order(1)
    void addRepeatedBaseServiceTest() throws ObjectExistException {

        BaseService baseService = BaseService.builder().name("BuildingDecoration").build();
        baseServiceServiceIMPL.addBaseService(baseService);

        BaseService repeatedBaseService = BaseService.builder().name("BuildingDecoration").build();

        Throwable exception = assertThrows(ObjectExistException.class, () -> baseServiceServiceIMPL.addBaseService(repeatedBaseService));
        assertEquals("This baseService is exist", exception.getMessage());

    }

    @Test
    @Order(2)
    void addBaseServiceTest() throws ObjectExistException {

        BaseService addedBaseService = BaseService.builder().name("BuildingFacilities").build();

        baseServiceServiceIMPL.addBaseService(addedBaseService);

        assertTrue(baseServiceServiceIMPL.getBaseServiceByName("BuildingFacilities").isPresent());

    }

    //getAllBaseService-------------------------------------------------------------------------------------------------------

    @Test
    @Order(3)
    void getAllBaseService() {

        List<BaseService> baseServiceList = baseServiceServiceIMPL.getAllBaseService();

        assertTrue(baseServiceList.size()>0);

    }

//getBaseServiceByName---------------------------------------------------------------------------------------------------------

    @Test()
    @Order(4)
    void getBaseServiceByName() {

        Optional<BaseService> optionalBaseService = baseServiceServiceIMPL.getBaseServiceByName("BuildingFacilities");
        assertTrue(optionalBaseService.isPresent());
    }
}