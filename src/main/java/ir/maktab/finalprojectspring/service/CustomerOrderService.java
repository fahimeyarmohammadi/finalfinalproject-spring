package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderService {

    void addOrder(CustomerOrder order);

    List<CustomerOrder> getAllCustomerOrderSInSubService(String subName);

    CustomerOrder getCustomerOrderById(Long id);

    void changeCustomerOrderConditionToWaitingForExpertSelection(Long id);

    void changeCustomerOrderConditionToWaitingForExpertComing(Long id);

    void changeCustomerOrderConditionToStarted(Long id);

    void changeCustomerOrderConditionToDone(Long id);

    void changeCustomerOrderConditionToPaid(Long id);

}
