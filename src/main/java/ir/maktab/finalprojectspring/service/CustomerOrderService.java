package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomerOrderService {

    void addOrder(CustomerOrder order);

    List<CustomerOrder> getAllCustomerOrderSInSubService(String subName);

    CustomerOrder getCustomerOrderById(Long id);

    List<CustomerOrder> getCustomerOrderWaitingExpertSelection(String username);

    List<CustomerOrder> getAllCustomerOrderDone(String username);

    void changeCustomerOrderConditionToWaitingForExpertSelection(Long id);

    void changeCustomerOrderConditionToWaitingForExpertComing(Long id);

    void changeCustomerOrderConditionToStarted(Long id);

    void changeCustomerOrderConditionToDone(Long id);

    void changeCustomerOrderConditionToPaid(Long id);

    List<CustomerOrder> getCustomerOrderByCondition(OrderRequestDto request);

    List<CustomerOrder> getAllCustomerOrder(String username);

    List<CustomerOrder> getCustomerOrderByManager(CustomerOrderRequestDto request);

    Specification selectByConditions(OrderRequestDto request);

    Specification selectOrderByManager(CustomerOrderRequestDto request);
}
