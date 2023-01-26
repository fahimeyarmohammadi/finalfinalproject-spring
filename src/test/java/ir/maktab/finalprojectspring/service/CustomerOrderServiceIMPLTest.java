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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static ir.maktab.finalprojectspring.data.model.enumeration.OrderCondition.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CustomerOrderServiceIMPLTest {

    @Autowired
    private CustomerOrderServiceIMPL customerOrderServiceIMPL;
    @Autowired
    private SubServiceServiceIMPL subServiceServiceIMPL;

    private CustomerOrder savedCustomerOrder;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("CustomerOrderServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    @Order(1)
    void addOrder() {

        Date preferDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 02, 11, 10, 30));
        SubService subService = subServiceServiceIMPL.getSubServiceByName("kitchen");
        CustomerOrder customerOrder = CustomerOrder.builder().description("firstOrder").proposedPrice(30e5).address(Address.builder().city("tehran").alley("ghadiyani").street("satarkhan").houseNumber("124").build()).preferDate(preferDate).build();
        customerOrder.setSubService(subService);
        customerOrderServiceIMPL.addOrder(customerOrder);
        savedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);
        assertEquals("firstOrder", savedCustomerOrder.getDescription());

    }

    @Test
    @Order(2)
    void changeCustomerOrderConditionToWaitingForExpertSelection() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertTrue(changedCustomerOrder.getOrderCondition().equals(WAITING_EXPERT_SELECTION));
    }

    @Test
    @Order(3)
    void changeCustomerOrderConditionToWaitingForExpertComing() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertTrue(changedCustomerOrder.getOrderCondition().equals(WAITING_FOR_EXPERT_COMING));
    }

    @Test()
    @Order(4)
    void changeCustomerOrderConditionToStarted() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToStarted(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertTrue(changedCustomerOrder.getOrderCondition().equals(STARTED));
    }

    @Test()
    @Order(5)
    void changeCustomerOrderConditionToDone() {

        customerOrderServiceIMPL.changeCustomerOrderConditionToDone(1L);

        CustomerOrder changedCustomerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        assertTrue(changedCustomerOrder.getOrderCondition().equals(DONE));
    }

    @Test
    @Order(6)
    void getCustomerOrderById(){

        CustomerOrder customerOrderById = customerOrderServiceIMPL.getCustomerOrderById(1L);
        assertEquals("firstOrder", customerOrderById.getDescription());
    }

    @Test
    @Order(7)
    void getAllCustomerOrderSInSubService() {

        Date preferDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 02,22 , 10, 30));
        SubService subService = subServiceServiceIMPL.getSubServiceByName("kitchen");
        CustomerOrder customerOrder = CustomerOrder.builder().description("secondOrder").proposedPrice(30e5).address(Address.builder().city("tehran").alley("ghadiyani").street("satarkhan").houseNumber("10").build()).preferDate(preferDate).build();
        customerOrder.setSubService(subService);
        customerOrderServiceIMPL.addOrder(customerOrder);

        List<CustomerOrder>customerOrderList=customerOrderServiceIMPL.getAllCustomerOrderSInSubService("kitchen");

        System.out.println(customerOrderList);

        assertTrue(customerOrderList.size()>0);

    }
}