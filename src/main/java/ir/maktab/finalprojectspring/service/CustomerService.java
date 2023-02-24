package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CardInformationDto;
import ir.maktab.finalprojectspring.data.dto.CustomerRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);

    void changPassword(String username, String repeatNewPassword, String newPassword);

    void customerGetOrder(CustomerOrder order, String username, Address address, SubService subService);

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    List<CustomerOrder> getAllCustomerOrders(String username);

    List<CustomerOrder> getOrdersWaitingExpertSelection(String username);

    List<CustomerOrder> getOrderDone(String username);

    void selectExpert(Offers offers);

    void changeCustomerOrderConditionToStarted(Offers offers);

    void changeCustomerOrderConditionToDone(Offers offers);

    Customer getByUsername(String username);

    void creditPayment(String customerUsername, Long customerOrderId);

    void onlinePayment(CardInformationDto cardInformation);

    void customerRegisterAReview(Review review, Offers offers, CustomerOrder customerOrder);

    List<Customer> searchAndFilterCustomer(CustomerRequestDto request);

    List<Offers> getOffersListOrderedByPrice(CustomerOrder order);

    List<Offers> getOffersListOrderedByExpertScore(CustomerOrder order);

    Specification selectByConditions(CustomerRequestDto request);

    Double getCredit();

    List<CustomerOrder> getCustomerOrderByCondition(OrderRequestDto request);
}
