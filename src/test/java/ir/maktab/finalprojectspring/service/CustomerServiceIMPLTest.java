package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceIMPLTest {

    @Autowired
    private CustomerServiceIMPL customerServiceIMPL;

    private Customer customer;

//    @BeforeAll
//    public void setUp(){
//
//        customer=Customer.builder().name("fahime123").familyName("yarmohammadi").email("fahimea@gmail").password("Fy123456").build();
//
//
//    }


    @Test
    @Order(1)
    void invalidNameAddCustomerTest() throws InvalidInputException {

        customer=Customer.builder().name("fahime123").familyName("yarmohammadi").email("fahimea@gmail").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () ->customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Name(Only Alphabetic Characters Accepted)", exception.getMessage());

    }

    @Test
    @Order(2)
    void invalidFamilyNameAddCustomerTest() throws InvalidInputException {

        customer=Customer.builder().name("fahime").familyName("yarmoha123mmadi").email("fahimea@gmail").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () ->customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Name(Only Alphabetic Characters Accepted)", exception.getMessage());

    }



    @Test
    void changPassword() {
    }

    @Test
    void signIn() {

    }

    @Test
    void customerGetOrder() {
    }

    @Test
    void getAllBaseService() {
    }

    @Test
    void getAllSubServiceInBaseService() {
    }

    @Test
    void getAllCustomerOrders() {
    }

    @Test
    void getOrdersWaitingExpertSelection() {
    }

    @Test
    void selectExpert() {
    }

    @Test
    void changeCustomerConditionToStarted() {
    }

    @Test
    void changeCustomerConditionToDone() {
    }
}