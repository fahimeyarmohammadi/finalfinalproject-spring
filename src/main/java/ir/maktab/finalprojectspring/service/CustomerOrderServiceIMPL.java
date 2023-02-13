package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.repository.CustomerOrderRepository;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerOrderServiceIMPL implements CustomerOrderService {

    private final CustomerOrderRepository orderRepository;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    @Transactional
    public void addOrder(CustomerOrder order) {
        if (order.getProposedPrice() < order.getSubService().getBasePrice())
            throw new InvalidInputException("the proposedPrice must greater than subServer basePrice");

        if (DateUtil.dateToLocalDateTime(order.getPreferDate()).isBefore(LocalDateTime.now()))
            throw new InvalidInputException("prefer Date must be after now");

        if (order.getAddress() == null)
            throw new InvalidInputException("you must have an address");

        order.setOrderCondition(OrderCondition.WAITING_EXPERT_SUGGESTION);
        orderRepository.save(order);
    }

    public List<CustomerOrder> getAllCustomerOrderSInSubService(String subName) {
        subServiceServiceIMPL.getSubServiceByName(subName);
        return orderRepository.getAllCustomerOrderInSubService(subName);
    }

    public CustomerOrder getCustomerOrderById(Long id) {
        Optional<CustomerOrder> optionalCustomerOrder = orderRepository.findById(id);
        return optionalCustomerOrder.orElseThrow(() -> new NotFoundException("Invalid Username"));
    }

    public void changeCustomerOrderConditionToWaitingForExpertSelection(Long id) {
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.WAITING_EXPERT_SELECTION);
        orderRepository.save(savedCustomerOrder);
    }

    public void changeCustomerOrderConditionToWaitingForExpertComing(Long id) {
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.WAITING_FOR_EXPERT_COMING);
        orderRepository.save(savedCustomerOrder);
    }

    public void changeCustomerOrderConditionToStarted(Long id) {
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.STARTED);
        orderRepository.save(savedCustomerOrder);
    }

    public void changeCustomerOrderConditionToDone(Long id) {
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.DONE);
        orderRepository.save(savedCustomerOrder);
    }

    public void changeCustomerOrderConditionToPaid(Long id){
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.PAID);
        orderRepository.save(savedCustomerOrder);
    }

}
