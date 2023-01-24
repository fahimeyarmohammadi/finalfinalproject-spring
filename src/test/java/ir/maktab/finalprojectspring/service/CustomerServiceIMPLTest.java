package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceIMPLTest {

    @Autowired
    private CustomerServiceIMPL customerServiceIMPL;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;



    @Test
    @Order(1)
    void invalidNameAddCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime123").familyName("yarmohammadi").email("fahimea@gmail").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Name(Only Alphabetic Characters Accepted)", exception.getMessage());

    }

    @Test
    @Order(2)
    void invalidFamilyNameAddCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmoha123mmadi").email("fahimea@gmail").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Name(Only Alphabetic Characters Accepted)", exception.getMessage());

    }

    @Test
    @Order(3)
    void invalidEmailAddCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime.gmail").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Email", exception.getMessage());

    }

    @Test
    @Order(4)
    void invalidPasswordAddCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy1234").build();
        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));
        assertEquals("Invalid Password( 8 characters,composition of character and digit)", exception.getMessage());

    }

    @Test
    @Order(5)
    void addCustomerTest() throws InvalidInputException {
        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").build();
        customerServiceIMPL.addCustomer(customer);
        Optional<Customer> savedCustomer = customerRepository.findByUsername(customer.getEmail());
        Customer saveCustomer = savedCustomer.get();
        Customer sCustomer = Customer.builder().name(saveCustomer.getName()).familyName(saveCustomer.getFamilyName()).email(saveCustomer.getEmail()).password(saveCustomer.getPassword()).build();
        assertEquals(customer, sCustomer);
    }

    @Test
    @Order(6)
    void repeatCustomerAddCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").build();
        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));
        assertEquals("Customer already exist with given email:" + customer.getEmail(), exception.getMessage());

    }
//changePassword---------------------------------------------------------------------------------------------------------

    @Test
    @Order(7)
    void invalidUserNameChangPasswordTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.changPassword("fahim@gmail.com", "Fahime12", "Fahime12"));
        assertEquals("Invalid Username", exception.getMessage());

    }

    @Test
    @Order(8)
    void invalidPasswordRepeatChangPasswordTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.changPassword("fahime@gmail.com", "Fahime12", "Fahime34"));
        assertEquals("password and repeatPassword must be equal", exception.getMessage());

    }

    @Test
    @Order(9)
    void changPasswordTestTest() throws InvalidInputException {

        customerServiceIMPL.changPassword("fahime@gmail.com", "Fahime12", "Fahime12");
        Customer savedCustomer = customerRepository.findByUsername("fahime@gmail.com").get();
        assertEquals("Fahime12", savedCustomer.getPassword());

    }

    //signIn-------------------------------------------------------------------------------------------------------------
    @Test
    void invalidUsernameSignInTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.signIn("fahime@gmail.co", "Fahime12"));
        assertEquals("Invalid Username", exception.getMessage());

    }

    @Test
    void invalidPasswordSignInTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.signIn("fahime@gmail.com", "Fahime1"));
        assertEquals("The password is not correct", exception.getMessage());
    }

    @Test
    void signInTest() throws InvalidInputException {

        Customer signInCustomer=customerServiceIMPL.signIn("fahime@gmail.com","Fahime12");
        assertNotNull(signInCustomer);

    }

    //customerGetOrder---------------------------------------------------------------------------------------------------------------
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