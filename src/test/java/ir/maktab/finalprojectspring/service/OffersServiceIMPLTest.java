package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.util.DateUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class OffersServiceIMPLTest {

    @Autowired
    private OffersServiceIMPL offersServiceIMPL;

    @Autowired
    private ExpertServiceIMPL expertServiceIMPL;

    @Autowired
    private CustomerOrderServiceIMPL customerOrderServiceIMPL;

    private CustomerOrder customerOrder;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {

        try (Connection connection = dataSource.getConnection()) {

            ScriptUtils.executeSqlScript(connection, new ClassPathResource("OffersServiceData.sql"));

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    //addOffers---------------------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    void addOffersTest() {

        Expert expert = expertServiceIMPL.getByUsername("fahime@gmail.com");

        customerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        Date startDate = DateUtil.localDateTimeToDate(LocalDateTime.of(2023, 2, 12, 10, 30));

        Offers offers = Offers.builder().offerPrice(32e5).startWork(startDate).build();

        offers.setCustomerOrder(customerOrder);

        offers.setExpert(expert);

        offers.setDuration(Duration.ofHours(3));

        offersServiceIMPL.addOffers(offers);

        Offers savedOffers = offersServiceIMPL.getOffersById(1L);

        assertEquals(32e5, savedOffers.getOfferPrice());

    }

    //getOffersListOrderedByPriceTest-----------------------------------------------------------------------------------------

    @Test
    @Order(2)
    void getOffersListOrderedByPriceTest() {

        customerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        List<Offers> offersList = offersServiceIMPL.getOffersListOrderedByPrice(customerOrder);

        assertTrue(offersList.get(0).getOfferPrice()<=offersList.get(1).getOfferPrice());

    }

    //getOffersListOrderedByExpertScoreTest---------------------------------------------------------------------------------

    @Test
    @Order(3)
    void getOffersListOrderedByExpertScoreTest() {

        customerOrder = customerOrderServiceIMPL.getCustomerOrderById(1L);

        List<Offers> offersList = offersServiceIMPL.getOffersListOrderedByExpertScore(customerOrder);

        assertTrue(offersList.get(0).getExpert().getScore()>=offersList.get(1).getExpert().getScore());

    }

    //getOffersByIdTest-----------------------------------------------------------------------------------------------------

    @Test
    @Order(4)
    void getOffersByIdTest(){

        Offers offers=offersServiceIMPL.getOffersById(1L);

        assertEquals(32e5,offers.getOfferPrice());

    }
}