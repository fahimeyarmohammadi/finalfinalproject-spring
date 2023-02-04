package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Address;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.util.DateUtil;
import org.junit.jupiter.api.*;
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

import static ir.maktab.finalprojectspring.data.enumeration.OrderCondition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerOrderServiceIMPLTest {

    @Autowired
    private CustomerOrderServiceIMPL customerOrderServiceIMPL;
    @Autowired
    private SubServiceServiceIMPL subServiceServiceIMPL;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("CustomerOrderServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //addOrder------------------------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    void addOrderTest() {

        Date preferDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 3, 25, 10, 30,00));

        SubService subService = subServiceServiceIMPL.getSubServiceByName("kitchen");

        CustomerOrder customerOrder = CustomerOrder.builder().description("firstOrder").proposedPrice(30e5).address(Address.builder().city("tehran").alley("ghadiyani").street("satarkhan").houseNumber("124").build()).preferDate(preferDate).build();

        customerOrder.setSubService(subService);

        customerOrderServiceIMPL.addOrder(customerOrder);

        CustomerOrder savedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals("firstOrder", savedCustomerOrder.getDescription());

    }

    //changeCustomerOrderConditionToWaitingForExpertSelection----------------------------------------------------------------

    @Test
    @Order(2)
    void changeCustomerOrderConditionToWaitingForExpertSelectionTest() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals(changedCustomerOrder.getOrderCondition(), WAITING_EXPERT_SELECTION);
    }

    //changeCustomerOrderConditionToWaitingForExpertComing--------------------------------------------------------------------

    @Test
    @Order(3)
    void changeCustomerOrderConditionToWaitingForExpertComingTest() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals(changedCustomerOrder.getOrderCondition(), WAITING_FOR_EXPERT_COMING);
    }

    //changeCustomerOrderConditionToStartedTest-------------------------------------------------------------------------

    @Test()
    @Order(4)
    void changeCustomerOrderConditionToStartedTest() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToStarted(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals(changedCustomerOrder.getOrderCondition(), STARTED);
    }

    //changeCustomerOrderConditionToDoneTest------------------------------------------------------------------------------

    @Test()
    @Order(5)
    void changeCustomerOrderConditionToDoneTest() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToDone(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals(changedCustomerOrder.getOrderCondition(), DONE);
    }

    //getCustomerOrderByIdTest----------------------------------------------------------------------------------------------

    @Test
    @Order(6)
    void getCustomerOrderByIdTest() {

        CustomerOrder customerOrderById = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertEquals("firstOrder", customerOrderById.getDescription());

    }

    //getAllCustomerOrderSInSubServiceTest--------------------------------------------------------------------------------

    @Test
    @Order(7)
    void getAllCustomerOrderSInSubServiceTest() {

        Date preferDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 2, 22, 10, 30));

        SubService subService = subServiceServiceIMPL.getSubServiceByName("kitchen");

        CustomerOrder customerOrder = CustomerOrder.builder().description("secondOrder").proposedPrice(30e5).address(Address.builder().city("tehran").alley("ghadiyani").street("satarkhan").houseNumber("10").build()).preferDate(preferDate).build();

        customerOrder.setSubService(subService);

        customerOrderServiceIMPL.addOrder(customerOrder);

        List<CustomerOrder> customerOrderList = customerOrderServiceIMPL.getAllCustomerOrderSInSubService("kitchen");

        System.out.println(customerOrderList);

        assertTrue(customerOrderList.size() > 0);

    }
}