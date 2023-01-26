package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.data.model.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class CustomerServiceIMPL implements CustomerService {

    private final CustomerRepository customerRepository;

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final CustomerOrderServiceIMPL orderServiceIMPL;

    public void addCustomer(Customer customer) throws InvalidInputException {

        Validation.validateName(customer.getName());
        Validation.validateName(customer.getFamilyName());
        Validation.validateEmail(customer.getEmail());
        Validation.validatePassword(customer.getPassword());
        customer.setCredit((double) 0);
        customer.setUsername(customer.getEmail());

        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException("Customer already exist with given email:" + customer.getEmail());

        }

    }

    public void changPassword(String username, String repeatNewPassword, String newPassword) throws InvalidInputException {

        if (!newPassword.equals(repeatNewPassword))
            throw new InvalidInputException("password and repeatPassword must be equal");

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        customer.setPassword(newPassword);

        customerRepository.save(customer);

    }

    public Customer signIn(String username, String password) throws InvalidInputException {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);
        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        if (!customer.getPassword().equals(password))
            throw new InvalidInputException("The password is not correct");
        return customer;

    }


    public void customerGetOrder(CustomerOrder order, String username) throws InvalidInputException {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);
        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        orderServiceIMPL.addOrder(order);

        customer.getOrderList().add(order);

        customerRepository.save(customer);

    }

    public List<BaseService> getAllBaseService() {
        return baseServiceServiceIMPL.getAllBaseService();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) throws NotFoundException {

        return subServiceServiceIMPL.getAllSubServiceInBaseService(baseServiceName);

    }

    public List<CustomerOrder> getAllCustomerOrders(String username) throws NotFoundException {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);
        Customer customer = signInCustomer.orElseThrow(() -> new NotFoundException("Invalid Username"));

        return customer.getOrderList();

    }

    public List<CustomerOrder> GetOrdersWaitingExpertSelection(String username) throws NotFoundException {

        List<CustomerOrder> orderList = getAllCustomerOrders(username);

        List<CustomerOrder> orderListWaitingForExpertSelection = new ArrayList<>();

        for (CustomerOrder c : orderList) {
            if (c.getOrderCondition().equals(OrderCondition.WAITING_EXPERT_SELECTION))
                orderListWaitingForExpertSelection.add(c);
        }

        return orderListWaitingForExpertSelection;

    }

    public void selectExpert(Offers offers) {

        orderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(offers.getCustomerOrder().getId());

    }

    public void changeCustomerOrderConditionToStarted(Offers offers) {

        orderServiceIMPL.changeCustomerOrderConditionToStarted(offers.getCustomerOrder().getId());

    }

    public void changeCustomerOrderConditionToDone(Offers offers) {

        orderServiceIMPL.changeCustomerOrderConditionToDone(offers.getCustomerOrder().getId());

    }

}