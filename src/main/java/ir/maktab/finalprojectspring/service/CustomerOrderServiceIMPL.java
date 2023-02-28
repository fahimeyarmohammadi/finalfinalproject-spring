package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.repository.CustomerOrderRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.DateUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        return optionalCustomerOrder.orElseThrow(() -> new NotFoundException("Invalid order Id"));
    }

    public List<CustomerOrder> getCustomerOrderWaitingExpertSelection(String username) {
        return orderRepository.getAllCustomerOrderWaitingExpertSelection(username);
    }

    public List<CustomerOrder> getAllCustomerOrderDone(String username) {
        return orderRepository.getAllCustomerOrderDone(username);
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
        savedCustomerOrder.setDoneDate(new Date());
        orderRepository.save(savedCustomerOrder);
    }

    public void changeCustomerOrderConditionToPaid(Long id) {
        CustomerOrder savedCustomerOrder = getCustomerOrderById(id);
        savedCustomerOrder.setOrderCondition(OrderCondition.PAID);
        orderRepository.save(savedCustomerOrder);
    }

    public List<CustomerOrder> getCustomerOrderByCondition(OrderRequestDto request) {
        return orderRepository.findAll(selectByConditions(request));
    }

    public List<CustomerOrder> getAllCustomerOrder(String username) {
        return orderRepository.getAllCustomerOrder(username);
    }

    public List<CustomerOrder> getCustomerOrderByManager(CustomerOrderRequestDto request) {
        return orderRepository.findAll(selectOrderByManager(request));
    }

    public Specification<CustomerOrder> selectByConditions(OrderRequestDto request) {
        return (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getCustomer() != null)
                predicateList.add(cb.equal(root.get("customer"), request.getCustomer()));
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0) {
                if( !(request.getOrderCondition().equals("WAITING_EXPERT_SUGGESTION")|| request.getOrderCondition().equals("WAITING_EXPERT_SELECTION")||
                        request.getOrderCondition().equals("WAITING_FOR_EXPERT_COMING")||request.getOrderCondition().equals("STARTED")||
                        request.getOrderCondition().equals("DONE")||request.getOrderCondition().equals("PAID")))
                    throw new InvalidInputException("orderCondition is false");

                predicateList.add(cb.equal(root.get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

    public Specification<CustomerOrder> selectOrderByManager(CustomerOrderRequestDto request) {
        return (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0){
                if( !(request.getOrderCondition().equals("WAITING_EXPERT_SUGGESTION")|| request.getOrderCondition().equals("WAITING_EXPERT_SELECTION")||
                        request.getOrderCondition().equals("WAITING_FOR_EXPERT_COMING")||request.getOrderCondition().equals("STARTED")||
                        request.getOrderCondition().equals("DONE")||request.getOrderCondition().equals("PAID")))
                    throw new InvalidInputException("orderCondition is false");
                predicateList.add(cb.equal(root.get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));}
            if (request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
                predicateList.add(cb.equal(root.get("subService").get("subName"), request.getSubServiceName()));
            if (request.getBaseServiceName() != null && request.getBaseServiceName().length() != 0)
                predicateList.add(cb.equal(root.get("subService").get("baseService").get("name"), request.getBaseServiceName()));
            if (request.getStartDate() != null && request.getStartDate().length() != 0) {
                try {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("preferDate"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getStartDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
            if (request.getEndDate() != null && request.getEndDate().length() != 0) {
                try {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("preferDate"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getEndDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}