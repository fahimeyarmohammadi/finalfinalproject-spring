package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubServiceServiceIMPLTest {

    @Autowired
    private SubServiceServiceIMPL subServiceServiceIMPL;

    @Autowired
    private BaseServiceServiceIMPL baseServiceServiceIMPL;

    private static SubService subService;

    private static BaseService baseService;

    @BeforeAll
    public static void setUp() {

        baseService = BaseService.builder().name("HomeAppliance").build();

        subService = SubService.builder().subName("KitchenAppliances").baseService(baseService).basePrice(12e4).description("Kitchen appliances").build();

    }

//addSubServiceTest----------------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    void addSubServiceTest() throws ObjectExistException, NotFoundException {

        baseServiceServiceIMPL.addBaseService(baseService);

        subServiceServiceIMPL.addSubService(subService);

        SubService saveSubService = subServiceServiceIMPL.getSubServiceByName("KitchenAppliances");

        assertEquals(subService, saveSubService);

    }

    @Test
    @Order(2)
    void addSubService_repeatedSubServiceTest() {

        SubService repeatedSubService = SubService.builder().subName("KitchenAppliances").baseService(baseService).build();

        Throwable exception = assertThrows(ObjectExistException.class, () -> subServiceServiceIMPL.addSubService(repeatedSubService));

        assertEquals("this subService is exist", exception.getMessage());

    }

    //getAllSubServiceTest-------------------------------------------------------------------------------------------------------

    @Test
    @Order(3)
    void getAllSubServiceTest() {

        List<SubService> subServiceList = subServiceServiceIMPL.getAllSubService();

        assertTrue(subServiceList.size() > 0);

    }

//getAllSubServiceInBaseService------------------------------------------------------------------------------------------

    @Test
    @Order(4)
    void getAllSubServiceInBaseServiceTest() {

        List<SubService> subServiceList = subServiceServiceIMPL.getAllSubServiceInBaseService("HomeAppliance");

        assertTrue(subServiceList.size() > 0);

    }

    //getSubServiceByName-------------------------------------------------------------------------------------------------------

    @Test
    @Order(5)
    void getSubServiceByName_InvalidSubServiceNameTest() {

        Throwable exception = assertThrows(NotFoundException.class, () -> subServiceServiceIMPL.getSubServiceByName("Appliances"));

        assertEquals("this subService not found", exception.getMessage());

    }

    @Test
    @Order(6)
    void getSubServiceByNameTest() {

        SubService savedSubService = subServiceServiceIMPL.getSubServiceByName("KitchenAppliances");

        assertEquals(subService, savedSubService);

    }


    //changeSubServiceBasePrice---------------------------------------------------------------------------------------------------

    @Test
    @Order(7)
    void changeSubServiceBasePriceTest() {

        subServiceServiceIMPL.changeSubServiceBasePrice("KitchenAppliances", 20e5);

        SubService savedSubService = subServiceServiceIMPL.getSubServiceByName("KitchenAppliances");

        assertEquals(20e5, savedSubService.getBasePrice());

    }

    //changeSubServiceDescription--------------------------------------------------------------------------------------------------

    @Test
    @Order(8)
    void changeSubServiceDescriptionTest() {

        subServiceServiceIMPL.changeSubServiceDescription("KitchenAppliances", "this is kitchen appliance");

        SubService savedSubService = subServiceServiceIMPL.getSubServiceByName("KitchenAppliances");

        assertEquals("this is kitchen appliance", savedSubService.getDescription());

    }

}