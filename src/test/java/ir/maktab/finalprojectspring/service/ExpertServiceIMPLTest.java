package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceIMPLTest {

    @Autowired
    private ExpertServiceIMPL expertServiceIMPL;

    @Autowired
    private ExpertRepository expertRepository;

     private Expert expert;

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
    }

    @Test
    @Order(4)
    void signIn() {
    }

    @Test
    @Order(5)
    void getByUsername() {

        Expert savedExpert=expertServiceIMPL.getByUsername("fahime@gmail.com");
        assertEquals("fahime",savedExpert.getName());

    }

    @Test
    void getExpertNotAccepted() {
    }

    @Test
    void acceptExpert() {
    }

    @Test
    void addSubServiceToExpertList() {
    }

    @Test
    void deleteSubServiceFromExpertList() {
    }

    @Test
    void getAllCustomerOrderInSubService() {
    }

    @Test
    void registerOffer() {
    }
}