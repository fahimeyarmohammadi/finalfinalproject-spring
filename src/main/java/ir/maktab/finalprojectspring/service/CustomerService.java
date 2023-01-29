package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);

    void changPassword(String username, String repeatNewPassword, String newPassword);

    Customer signIn(String username, String password);

    void customerGetOrder(CustomerOrder order,String username);

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    List<CustomerOrder> getAllCustomerOrders(String username);

    List<CustomerOrder>getOrdersWaitingExpertSelection(String username);

    void selectExpert(Offers offers);

    void changeCustomerOrderConditionToStarted(Offers offers);

    void changeCustomerOrderConditionToDone(Offers offers);
}
