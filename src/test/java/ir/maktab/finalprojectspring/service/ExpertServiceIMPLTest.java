package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ir.maktab.finalprojectspring.data.enumeration.ExpertCondition.ACCEPTED;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceIMPLTest {

    @Autowired
    private ExpertServiceIMPL expertServiceIMPL;

    @Autowired
    private ExpertRepository expertRepository;

     private Expert expert;


    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("ExpertServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Expert[] expertData() {

        Expert[] experts = new Expert[6];

        experts[0] = Expert.builder().name("fahime123").familyName("yarmohammadi").email("fahimea@gmail").password("Fy123456").path("image/valid.jpg").build();
        experts[1] = Expert.builder().name("fahime").familyName("yarmoha123mmadi").email("fahimea@gmail").password("Fy123456").path("image/valid.jpg").build();
        experts[2] = Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime.gmail").password("Fy123456").path("image/valid.jpg").build();
        experts[3] = Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy1234").path("image/valid.jpg").build();
        experts[4] =Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").path("image/format.png").build();
        experts[5] =Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").path("image/bigSize.jpg").build();
        experts[5] =Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").path("image/bigSize.jpg").build();
        return experts;

    }

    @ParameterizedTest
    @MethodSource(value = "expertData")
    @Order(1)

    void addExpertInvalidInformationTest(Expert invalidExpert) {
        Throwable exception = assertThrows(InvalidInputException.class, () -> expertServiceIMPL.addExpert(invalidExpert));
        assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    @Order(2)
    void addExpertTest() throws IOException {

        expert = Expert.builder().name("fahime").familyName("yarmohammadi").email("fahime@gmail.com").password("Fy123456").path("image/valid.jpg").build();
        expertServiceIMPL.addExpert(expert);
        Optional<Expert> savedExpert = expertRepository.findByUsername("fahime@gmail.com");
        Expert saveExpert = savedExpert.get();
        Expert sExpert = Expert.builder().name(saveExpert.getName()).familyName(saveExpert.getFamilyName()).email(saveExpert.getEmail()).password(saveExpert.getPassword()).path(expert.getPath()).build();
       assertEquals("fahime",sExpert.getName());

    }
//changePassword-----------------------------------------------------------------------------------------------------
    @Test
    @Order(3)
    void changPassword() {

       expertServiceIMPL.changPassword("fahime@gmail.com", "Fahime12", "Fahime12");
        Expert savedExpert = expertRepository.findByUsername("fahime@gmail.com").get();
        assertEquals("Fahime12", savedExpert.getPassword());

    }
//signIn---------------------------------------------------------------------------------------------------------------
    @Test
    @Order(4)
    void signIn() {

        Expert signInExpert = expertServiceIMPL.signIn("fahime@gmail.com", "Fahime12");
        assertNotNull(signInExpert);

    }
//getByUserName--------------------------------------------------------------------------------------------------------------

    @Test
    @Order(5)
    void getByUsername() {

        Expert savedExpert=expertServiceIMPL.getByUsername("fahime@gmail.com");
        assertEquals("fahime",savedExpert.getName());

    }
//getExpertNotAccepted----------------------------------------------------------------------------------------------------
    @Test
    @Order(6)
    void getExpertNotAccepted() {

        List<Expert>expertList=expertServiceIMPL.getExpertNotAccepted();
        assertTrue(expertList.size()>0);
    }
//acceptExpert------------------------------------------------------------------------
    @Test
    @Order(7)
    void acceptExpert() {
        expertServiceIMPL.acceptExpert("fahime@gmail.com");
        Expert acceptedExpert=expertServiceIMPL.getByUsername("fahime@gmail.com");
        assertTrue(acceptedExpert.getExpertCondition().equals(ACCEPTED));

    }
//addSubServiceToExpertList-----------------------------------------------------------
    @Test
    @Order(8)
    void addSubServiceToExpertList() {
        expertServiceIMPL.addSubServiceToExpertList("fahime@gmail.com","kitchen");
        expertServiceIMPL.addSubServiceToExpertList("fahime@gmail.com","bathroom");
        Expert savedExpert=expertServiceIMPL.getByUsername("fahime@gmail.com");
        assertTrue(savedExpert.getSubServiceList().size()>0);
    }
//deleteSubServiceFromExpertList--------------------------------------------------------
    @Test
    @Order(9)
    void deleteSubServiceFromExpertList() {
        int listSize=expertServiceIMPL.getByUsername("fahime@gmail.com").getSubServiceList().size();
        expertServiceIMPL.deleteSubServiceFromExpertList("fahime@gmail.com","kitchen");
        int afterDeleteListSize=expertServiceIMPL.getByUsername("fahime@gmail.com").getSubServiceList().size();
        assertTrue(afterDeleteListSize==listSize-1);
    }

    //convertArrayByteToImage-------------------------------------------------------------------------------
    @Test
    @Order(10)
   void  convertArrayByteToImage() throws IOException {

        Expert savedExpert=expertServiceIMPL.getByUsername("fahime@gmail.com");
        assertFalse(expertServiceIMPL.convertArrayByteToImage(savedExpert.getImage()).isEmpty());

    }
}