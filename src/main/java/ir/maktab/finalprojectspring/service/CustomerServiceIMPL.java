package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
        customerRepository.save(customer);

    }

    public void changPassword(String username, String repeatNewPassword, String newPassword) throws InvalidInputException {

        if (!newPassword.equals(repeatNewPassword))
            throw new InvalidInputException("password and repeatPassword must be equal");

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        customerRepository.updateCustomerPassword(newPassword, username);

    }

    public Customer signIn(String username, String password) throws NotFoundException {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);
        Customer customer = signInCustomer.orElseThrow(() -> new NotFoundException("Invalid Username"));
        if (!customer.getPassword().equals(password))
            throw new NotFoundException("the password is not correct");
        return customer;

    }

    public void customerGetOrder(CustomerOrder order,String username) throws NotFoundException, InvalidInputException {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);
        Customer customer = signInCustomer.orElseThrow(() -> new NotFoundException("Invalid Username"));

        orderServiceIMPL.addOrder(order);

        customer.getOrderList().add(order);

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

    public List<CustomerOrder>GetOrdersWaitingExpertSelection(String username) throws NotFoundException {

        List<CustomerOrder>orderList=getAllCustomerOrders(username);

        List<CustomerOrder>orderListWaitingForExpertSelection = new ArrayList<>();

        for (CustomerOrder c:orderList) {
            if(c.getOrdercondition().equals(OrderCondition.WAITING_EXPERT_SELECTION))
                orderListWaitingForExpertSelection.add(c);
        }

         return orderListWaitingForExpertSelection;

    }
    public void selectExpert(Offers offers){

        orderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(offers.getCustomerOrder());

    }

    public void changeCustomerConditionToStarted(Offers offers){

        orderServiceIMPL.changeCustomerOrderConditionToStarted(offers.getCustomerOrder());

    }

    public void changeCustomerConditionToDone(Offers offers){

        orderServiceIMPL.changeCustomerOrderConditionToDone(offers.getCustomerOrder());

    }



}