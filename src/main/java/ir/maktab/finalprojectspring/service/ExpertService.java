package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.ExpertRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.model.Review;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.List;

public interface ExpertService {

    void addExpert(Expert expert);

    void changPassword(String username, String repeatNewPassword, String newPassword);

    Expert getByUsername(String username);

    List<Expert> getExpertNotAccepted();

    void acceptExpert(String username);

    void addSubServiceToExpertList(String username, String subServiceName);

    void deleteSubServiceFromExpertList(String username, String subServiceName);

    List<CustomerOrder> getAllCustomerOrderInSubService(String userName);

    void registerOffer(Offers offers, Expert expert, CustomerOrder customerOrder);

    String convertArrayByteToImage(String username) throws IOException;

    void minusExpertScore(Offers offers);

    void inActiveExpert(Expert expert);

    void increaseExpertCredit(Offers offers);

    int expertGetOffersScore(Long offersId);

    void addReviewToExpertReviewList(Review review);

    List<Expert> searchAndFilterExpert(ExpertRequestDto request);

    boolean verify(String verificationCode);

    void sendVerificationEmail(Expert expert);

    Specification<Expert> selectByConditions(ExpertRequestDto request);

    List<Offers> getCustomerOrderByCondition(OrderRequestDto request);

    void updateExpert(Expert expert);
}
