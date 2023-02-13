package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CardInformation;
import ir.maktab.finalprojectspring.data.dto.CustomerRequestDto;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {

    private final CustomerRepository customerRepository;

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final CustomerOrderServiceIMPL orderServiceIMPL;

    private final OffersServiceIMPL offersServiceIMPL;

    private final ExpertServiceIMPL expertServiceIMPL;

    private final ReviewServiceIMPL reviewServiceIMPL;

    public void addCustomer(Customer customer) {

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

    public void changPassword(String username, String repeatNewPassword, String newPassword) {

        if (!(newPassword.equals(repeatNewPassword))) {
            throw new InvalidInputException("password and repeatPassword must be equal");
        }

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        customer.setPassword(newPassword);

        customerRepository.save(customer);

    }

    public Customer signIn(String username, String password) {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        Customer customer = signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        if (!customer.getPassword().equals(password))

            throw new InvalidInputException("The password is not correct");

        return customer;

    }

    @Transactional
    public void customerGetOrder(CustomerOrder order, String username) {

        Customer customer = getByUsername(username);

        orderServiceIMPL.addOrder(order);

        customer.getOrderList().add(order);

        customerRepository.save(customer);

    }

    public List<BaseService> getAllBaseService() {
        return baseServiceServiceIMPL.getAllBaseService();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) {

        return subServiceServiceIMPL.getAllSubServiceInBaseService(baseServiceName);

    }

    public List<CustomerOrder> getAllCustomerOrders(String username) {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        Customer customer = signInCustomer.orElseThrow(() -> new NotFoundException("Invalid Username"));

        return customer.getOrderList();

    }

    public List<CustomerOrder> getOrdersWaitingExpertSelection(String username) {

        List<CustomerOrder> orderList = getAllCustomerOrders(username);

        List<CustomerOrder> orderListWaitingForExpertSelection = new ArrayList<>();

        for (CustomerOrder c : orderList) {

            if (c.getOrderCondition().equals(OrderCondition.WAITING_EXPERT_SELECTION))

                orderListWaitingForExpertSelection.add(c);

        }
        return orderListWaitingForExpertSelection;

    }

    @Transactional
    public void selectExpert(Offers offers) {

        Offers savedOffer = offersServiceIMPL.getOffersById(offers.getId());

        orderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(offers.getCustomerOrder().getId());

        savedOffer.setAcceptOffer(true);

        offersServiceIMPL.updateOffers(savedOffer);

    }

    public void changeCustomerOrderConditionToStarted(Offers offers) {

        if (new Date().before(offers.getStartWork()))

            throw new InvalidInputException("when you want to change order condition to start must be work started!!");

        orderServiceIMPL.changeCustomerOrderConditionToStarted(offers.getCustomerOrder().getId());

    }

    public void changeCustomerOrderConditionToDone(Offers offers) {

        orderServiceIMPL.changeCustomerOrderConditionToDone(offers.getCustomerOrder().getId());

    }

    public Customer getByUsername(String username) {

        Optional<Customer> signInCustomer = customerRepository.findByUsername(username);

        return signInCustomer.orElseThrow(() -> new InvalidInputException("Invalid Username"));

    }


    @Transactional
    public void creditPayment(String customerUsername,Long customerOrderId) {

        Offers offers = offersServiceIMPL.getOffersByCustomerOrderIdAndOffersCondition(customerOrderId);

        Customer customer = getByUsername(customerUsername);

        if (customer.getCredit() < offers.getOfferPrice())

            throw new InvalidInputException("your credit not enough");

        customer.setCredit(customer.getCredit() - offers.getOfferPrice());

        customerRepository.save(customer);

        orderServiceIMPL.changeCustomerOrderConditionToPaid(customerOrderId);

        expertServiceIMPL.minusExpertScore(offers);

        expertServiceIMPL.increaseExpertCredit(offers);

    }

    @Transactional
    public void onlinePayment(CardInformation cardInformation) {

        Offers offers = offersServiceIMPL.getOffersByCustomerOrderIdAndOffersCondition(Long.valueOf(cardInformation.getCustomerOrderId()));

        Validation.validateCardNumber(cardInformation.getCardNumber());

        Validation.validateCvv2(cardInformation.getCvv2());

        orderServiceIMPL.changeCustomerOrderConditionToPaid(Long.valueOf(cardInformation.getCustomerOrderId()));

        expertServiceIMPL.minusExpertScore(offers);

        expertServiceIMPL.increaseExpertCredit(offers);

    }

    public void customerRegisterAReview(Review review){

        if (! review.getCustomerOrder().getOrderCondition().equals(OrderCondition.DONE))

            throw new InvalidInputException("you must after done work get review!!");

        reviewServiceIMPL.addReview(review);

        expertServiceIMPL.addReviewToExpertReviewList(review);

    }

    public List<Customer> searchAndFilterCustomer(CustomerRequestDto request){

        return customerRepository.findAll(CustomerRepository.selectByConditions(request));
    }
}