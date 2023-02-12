package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CardInformation;
import ir.maktab.finalprojectspring.data.model.*;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);

    void changPassword(String username, String repeatNewPassword, String newPassword);

    Customer signIn(String username, String password);

    void customerGetOrder(CustomerOrder order, String username);

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    List<CustomerOrder> getAllCustomerOrders(String username);

    List<CustomerOrder> getOrdersWaitingExpertSelection(String username);

    void selectExpert(Offers offers);

    void changeCustomerOrderConditionToStarted(Offers offers);

    void changeCustomerOrderConditionToDone(Offers offers);

    Customer getByUsername(String username);


   void creditPayment(String customerUsername,Long customerOrderId);

   void onlinePayment(CardInformation cardInformation);

}
