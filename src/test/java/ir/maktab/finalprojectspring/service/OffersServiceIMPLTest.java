package ir.maktab.finalprojectspring.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OffersServiceIMPLTest {

    @Autowired
    private OffersServiceIMPL offersServiceIMPL;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("OffersServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addOffers() {

    }

    @Test
    void getOffersListOrderedByPrice() {
    }

    @Test
    void getOffersListOrderedByExpertScore() {
    }
}