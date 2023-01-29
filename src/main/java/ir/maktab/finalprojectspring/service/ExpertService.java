package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Offers;

import java.io.IOException;
import java.util.List;

public interface ExpertService {

    void addExpert(Expert expert) throws IOException;

    void changPassword(String username, String repeatNewPassword, String newPassword);

    Expert signIn(String username, String password);

    Expert getByUsername(String username);

    List<Expert> getExpertNotAccepted();

    void acceptExpert(String username);

    void addSubServiceToExpertList(String username, String subServiceName);

    void deleteSubServiceFromExpertList(String username, String subServiceName);

    List<CustomerOrder> getAllCustomerOrderInSubService(String userName);

    void registerOffer(Offers offers);

    String convertArrayByteToImage(String username) throws IOException;

}
