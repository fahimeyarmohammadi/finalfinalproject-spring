package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.util.DateUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CustomerServiceIMPLTest {

    @Autowired
    private CustomerServiceIMPL customerServiceIMPL;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired

    private SubServiceServiceIMPL subServiceServiceIMPL;

    @Autowired

    private CustomerOrderServiceIMPL customerOrderServiceIMPL;

    @Autowired

    private OffersServiceIMPL offersServiceIMPL;

    private Customer customer;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {

        try (Connection connection = dataSource.getConnection()) {

            ScriptUtils.executeSqlScript(connection, new ClassPathResource("CustomerServiceData.sql"));

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public static Customer[] customerData() {

        Customer[] customers = new Customer[4];

        customers[0] = Customer.builder().name("fahime123").familyName("yarmohammadi").email("fahimea@gmail").password("Fy123456").build();

        customers[1] = Customer.builder().name("fahime").familyName("yarmoha123mmadi").email("fahimea@gmail").password("Fy123456").build();

        customers[2] = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime.gmail").password("Fy123456").build();

        customers[3] = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy1234").build();

        return customers;

    }

    //addCustomer----------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource(value = "customerData")
    @Order(1)
    void addCustomer_InvalidInputTest(Customer invalidCustomer){

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(invalidCustomer));

        assertEquals("Invalid input", exception.getMessage());

    }

    @Test
    @Order(2)
    void addCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").build();

        customerServiceIMPL.addCustomer(customer);

        Optional<Customer> savedCustomer = customerRepository.findByUsername(customer.getEmail());

        Customer saveCustomer = savedCustomer.get();

        Customer sCustomer = Customer.builder().name(saveCustomer.getName()).familyName(saveCustomer.getFamilyName()).email(saveCustomer.getEmail()).password(saveCustomer.getPassword()).build();

        assertEquals(customer, sCustomer);

    }

    @Test
    @Order(3)
    void addCustomer_RepeatCustomerTest() throws InvalidInputException {

        customer = Customer.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").build();

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.addCustomer(customer));

        assertEquals("Customer already exist with given email:" + customer.getEmail(), exception.getMessage());

    }
//changePassword---------------------------------------------------------------------------------------------------------

    @Test
    @Order(4)
    void changPassword_InvalidUserNameTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.changPassword("fahim@gmail.com", "Fahime12", "Fahime12"));

        assertEquals("Invalid Username", exception.getMessage());

    }

    @Test
    @Order(5)
    void changPassword_InvalidPasswordRepeatTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.changPassword("fahime@gmail.com", "Fahime12", "Fahime34"));

        assertEquals("password and repeatPassword must be equal", exception.getMessage());

    }

    @Test
    @Order(6)
    void changPasswordTest() throws InvalidInputException {

        customerServiceIMPL.changPassword("fahime@gmail.com", "Fahime12", "Fahime12");

        Customer savedCustomer = customerRepository.findByUsername("fahime@gmail.com").get();

        assertEquals("Fahime12", savedCustomer.getPassword());

    }

    //signIn-------------------------------------------------------------------------------------------------------------
    @Test
    @Order(7)
    void SignIn_InvalidUsernameTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.signIn("fahime@gmail.co", "Fahime12"));

        assertEquals("Invalid Username", exception.getMessage());

    }

    @Test
    @Order(8)
    void signIn_InvalidPasswordTest() {

        Throwable exception = assertThrows(InvalidInputException.class, () -> customerServiceIMPL.signIn("fahime@gmail.com", "Fahime1"));

        assertEquals("The password is not correct", exception.getMessage());
    }

    @Test
    @Order(9)
    void signInTest() throws InvalidInputException {

        Customer signInCustomer = customerServiceIMPL.signIn("fahime@gmail.com", "Fahime12");

        assertNotNull(signInCustomer);

    }

    //customerGetOrder---------------------------------------------------------------------------------------------------------------

    @Test
    @Order(10)
    void customerGetOrderTest() {

        Date preferDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 2, 11, 10, 30));

        SubService subService = subServiceServiceIMPL.getSubServiceByName("kitchen");

        CustomerOrder customerOrder = CustomerOrder.builder().description("firstOrder").proposedPrice(30e5).address(Address.builder().city("tehran").alley("ghadiyani").street("satarkhan").houseNumber("124").build()).preferDate(preferDate).build();

        customerOrder.setSubService(subService);

        customerServiceIMPL.customerGetOrder(customerOrder, "fahime@gmail.com");

        assertTrue(customerRepository.findByUsername("fahime@gmail.com").get().getOrderList().size() > 0);
    }

    //getAllCustomerOrders---------------------------------------------------------------------------------------------------------

    @Test
    @Order(11)
    void getAllCustomerOrdersTest() {

        List<CustomerOrder> customerOrderList = customerServiceIMPL.getAllCustomerOrders("fahime@gmail.com");

        assertTrue(customerOrderList.size() > 0);

    }

//getOrdersWaitingExpertSelection---------------------------------------------------------------------------------------------

    @Test
    @Order(12)
    void getOrdersWaitingExpertSelectionTest() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(1L);

        List<CustomerOrder> customerOrderList = customerServiceIMPL.getOrdersWaitingExpertSelection("fahime@gmail.com");

        assertTrue(customerOrderList.size() > 0);

    }

    //selectExpert-----------------------------------------------------------------------------------------------------------------

    @Test
    @Order(13)
    void selectExpertTest() {

        Offers offers=offersServiceIMPL.getOffersById(1L);

        offers.setCustomerOrder(customerOrderServiceIMPL.getCustomerOrderById(1L));

        customerServiceIMPL.selectExpert(offers);

        Offers acceptOffers=offersServiceIMPL.getOffersById(1L);

        assertTrue(acceptOffers.isAcceptOffer());

    }
}