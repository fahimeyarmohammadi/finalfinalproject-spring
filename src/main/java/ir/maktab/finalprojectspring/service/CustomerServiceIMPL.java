package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CardInformationDto;
import ir.maktab.finalprojectspring.data.dto.CustomerRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.enumeration.UserType;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final BCryptPasswordEncoder passwordEncoder;

    public void addCustomer(Customer customer) {
        // Validation.validateName(customer.getName());
        // Validation.validateName(customer.getFamilyName());
        // Validation.validateEmail(customer.getEmail());
        // Validation.validatePassword(customer.getPassword());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserType(UserType.ROLE_CUSTOMER);
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
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    @Transactional
    public void customerGetOrder(CustomerOrder order, String username, Address address, SubService subService) {
        Customer customer = getByUsername(username);
        customer.setCustomerOrderNumber(customer.getCustomerOrderNumber() + 1);
        order.setCustomer(customer);
        order.setAddress(address);
        order.setSubService(subService);
        orderServiceIMPL.addOrder(order);
        customerRepository.save(customer);
    }

    public List<BaseService> getAllBaseService() {
        return baseServiceServiceIMPL.getAllBaseService();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) {
        return subServiceServiceIMPL.getAllSubServiceInBaseService(baseServiceName);
    }

    public List<CustomerOrder> getAllCustomerOrders(String username) {
        Customer customer = getByUsername(username);
        return orderServiceIMPL.getAllCustomerOrder(username);
    }

    public List<CustomerOrder> getOrdersWaitingExpertSelection(String username) {
        Customer customer = getByUsername(username);
        return orderServiceIMPL.getCustomerOrderWaitingExpertSelection(username);
    }

    public List<CustomerOrder> getOrderDone(String username) {
        Customer customer = getByUsername(username);
        return orderServiceIMPL.getAllCustomerOrderDone(username);
    }

    @Transactional
    public void selectExpert(Offers offers) {
        Offers savedOffer = offersServiceIMPL.getOffersById(offers.getId());
        orderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertComing(offers.getCustomerOrder().getId());
        Expert expert = savedOffer.getExpert();
        expert.setCustomerOrderNumber(expert.getCustomerOrderNumber() + 1);
        savedOffer.setAcceptOffer(true);
        expertServiceIMPL.updateExpert(expert);
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
    public void creditPayment(String customerUsername, Long customerOrderId) {
        if (!(orderServiceIMPL.getCustomerOrderById(customerOrderId).getOrderCondition().equals(OrderCondition.DONE)))
            throw new InvalidInputException("your order must be done for pay");
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
    public void onlinePayment(CardInformationDto cardInformation) {
        if (!(orderServiceIMPL.getCustomerOrderById(Long.valueOf(cardInformation.getCustomerOrderId())).getOrderCondition().equals(OrderCondition.DONE)))
            throw new InvalidInputException("your order must be done for pay");

        Offers offers = offersServiceIMPL.getOffersByCustomerOrderIdAndOffersCondition(Long.valueOf(cardInformation.getCustomerOrderId()));
        Validation.validateCardNumber(cardInformation.getCardNumber());
        Validation.validateCvv2(cardInformation.getCvv2());
        orderServiceIMPL.changeCustomerOrderConditionToPaid(Long.valueOf(cardInformation.getCustomerOrderId()));
        expertServiceIMPL.minusExpertScore(offers);
        expertServiceIMPL.increaseExpertCredit(offers);
    }

    public void customerRegisterAReview(Review review, Offers offers, CustomerOrder customerOrder) {
        if (!((customerOrder.getOrderCondition().equals(OrderCondition.DONE)) || (review.getCustomerOrder().getOrderCondition().equals(OrderCondition.PAID))))
            throw new InvalidInputException("you must after done work get review!!");
        review.setOffers(offers);
        review.setCustomerOrder(customerOrder);
        reviewServiceIMPL.addReview(review);
        expertServiceIMPL.addReviewToExpertReviewList(review);
    }

    public List<Customer> searchAndFilterCustomer(CustomerRequestDto request) {
        return customerRepository.findAll(selectByConditions(request));
    }

    public Specification selectByConditions(CustomerRequestDto request) {
        return (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getName() != null && request.getName().length() != 0)
                predicateList.add(cb.equal(root.get("name"), request.getName()));
            if (request.getFamilyName() != null && request.getFamilyName().length() != 0)
                predicateList.add(cb.equal(root.get("familyName"), request.getFamilyName()));
            if (request.getEmail() != null && request.getEmail().length() != 0)
                predicateList.add(cb.equal(root.get("email"), request.getEmail()));
            if (request.getStartDate() != null && request.getStartDate().length() != 0) {
                try {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("date"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getStartDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
            if (request.getEndDate() != null && request.getEndDate().length() != 0) {
                try {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("date"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getEndDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }

            if (request.getCustomerOrderMinNumber() != null && request.getCustomerOrderMinNumber().length() != 0)
                predicateList.add(cb.greaterThanOrEqualTo(root.get("customerOrderNumber"), Integer.valueOf(request.getCustomerOrderMinNumber())));
            if (request.getCustomerOrderMaxNumber() != null && request.getCustomerOrderMaxNumber().length() != 0)
                predicateList.add(cb.lessThanOrEqualTo(root.get("customerOrderNumber"), request.getCustomerOrderMaxNumber()));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }


    public List<Offers> getOffersListOrderedByPrice(CustomerOrder order) {
        return offersServiceIMPL.getOffersListOrderedByPrice(order);
    }

    public List<Offers> getOffersListOrderedByExpertScore(CustomerOrder order) {
        return offersServiceIMPL.getOffersListOrderedByExpertScore(order);
    }

    public Double getCredit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer customer = getByUsername(userDetails.getUsername());
        return customer.getCredit();
    }

    public List<CustomerOrder> getCustomerOrderByCondition(OrderRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.setCustomer(getByUsername(userDetails.getUsername()));
        return orderServiceIMPL.getCustomerOrderByCondition(request);
    }
}