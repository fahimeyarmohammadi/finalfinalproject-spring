package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.repository.CustomerOrderRepository;
import ir.maktab.finalprojectspring.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceIMPL implements CustomerOrderService{

    private final CustomerOrderRepository orderRepository;

    public void addOrder(CustomerOrder order) throws InvalidInputException {

        if (order.getProposedPrice() < order.getSubService().getBasePrice())
            throw new InvalidInputException("the proposedPrice must greater than subServer basePrice");

        if (DateUtil.dateToLocalDateTime(order.getPreferDate()).isBefore(LocalDateTime.now()))
            throw new InvalidInputException("prefer Date must be after now");

        if (order.getAddress() == null)
            throw new InvalidInputException("you must have an address");

        order.setOrdercondition(OrderCondition.WAITING_EXPERT_SUGGESTION);

        orderRepository.save(order);
    }
}
