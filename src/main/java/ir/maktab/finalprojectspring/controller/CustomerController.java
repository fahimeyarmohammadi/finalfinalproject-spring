package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.*;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.mapper.*;
import ir.maktab.finalprojectspring.service.CustomerOrderServiceIMPL;
import ir.maktab.finalprojectspring.service.CustomerServiceIMPL;
import ir.maktab.finalprojectspring.service.OffersServiceIMPL;
import ir.maktab.finalprojectspring.service.SubServiceServiceIMPL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {

    private String message;

    private final CustomerServiceIMPL customerServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final OffersServiceIMPL offersServiceIMPL;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;


    @PostMapping("/register")
    public String register(@Valid @RequestBody CustomerDto customerDto) {

        Customer customer = CustomerMapper.INSTANCE.dtoToModel(customerDto);

        customerServiceIMPL.addCustomer(customer);

        return "you register successfully";
    }

    @PostMapping("/logIn")
    public String logIn(@Valid @RequestBody CustomerLogInDto customerLogInDto) {

        customerServiceIMPL.signIn(customerLogInDto.getUsername(), customerLogInDto.getPassword());

        return "ok";

    }

    @PutMapping("/changePassword")

    public String changePassword(@Valid @RequestBody CustomerChangePasswordDto customerChangePasswordDto) {

        customerServiceIMPL.changPassword(customerChangePasswordDto.getUsername(), customerChangePasswordDto.getRepeatNewPassword(), customerChangePasswordDto.getNewPassword());

        return "your password changed!";
    }

    @GetMapping("/getAllBaseService")
    public List<BaseServiceDto> getAllBaseService() {

        List<BaseService> baseServiceList = customerServiceIMPL.getAllBaseService();
        return BaseServiceMapper.INSTANCE.listToDtoList(baseServiceList);

    }

    @GetMapping("/getAllSubServiceInBaseService")
    public List<SubServiceDto> getAllSubServiceInBaseService(@RequestParam String baseServiceName) {

        List<SubService> subServiceList = customerServiceIMPL.getAllSubServiceInBaseService(baseServiceName);
        return SubServiceMapper.INSTANCE.listToDtoList(subServiceList);

    }

    @PostMapping("/customerGetOrder")

    public String customerGetOrder(@Valid @RequestBody CustomerOrderDto customerOrderDto, @RequestParam String username, @RequestParam String subServiceName) {

        CustomerOrder customerOrder = CustomerOrderMapper.INSTANCE.dtoToModel(customerOrderDto);

        customerOrder.setSubService(subServiceServiceIMPL.getSubServiceByName(subServiceName));

        Address address = AddressMapper.INSTANCE.dtoToModel(customerOrderDto.getAddressDto());

        customerOrder.setAddress(address);

        customerServiceIMPL.customerGetOrder(customerOrder, username);

        return "your order save";
    }

    @GetMapping("/getAllCustomerOrder")
    public List<CustomerOrderDto> getAllCustomerOrder(@RequestParam String username) {

        List<CustomerOrder> customerOrderList = customerServiceIMPL.getAllCustomerOrders(username);

        return CustomerOrderMapper.INSTANCE.listToDtoList(customerOrderList);
    }

    @GetMapping("/getOrdersWaitingExpertSelection")
    public List<CustomerOrderDto> getOrdersWaitingExpertSelection(@RequestParam String username) {

        List<CustomerOrder> customerOrderList = customerServiceIMPL.getOrdersWaitingExpertSelection(username);
        return CustomerOrderMapper.INSTANCE.listToDtoList(customerOrderList);
    }

    @PutMapping("/selectExpert")

    public String selectExpert(@RequestParam Long offersId) {

        Offers offers = offersServiceIMPL.getOffersById(offersId);

        customerServiceIMPL.selectExpert(offers);

        return "your expert selected";

    }

    @PutMapping("/changeCustomerOrderConditionToStarted")
    public String changeCustomerOrderConditionToStarted(@RequestParam Long offersId) {

        Offers offers = offersServiceIMPL.getOffersById(offersId);

        customerServiceIMPL.changeCustomerOrderConditionToStarted(offers);

        return "your order started";

    }

    @PutMapping("/changeCustomerOrderConditionToDone")
    public String changeCustomerOrderConditionToDone(@RequestParam Long offersId) {

        Offers offers = offersServiceIMPL.getOffersById(offersId);

        customerServiceIMPL.changeCustomerOrderConditionToDone(offers);

        return "your order done!";

    }

    @PutMapping("/creditPayment")
    public String creditPayment(@RequestParam String customerUsername, @RequestParam Long customerOrderId) {

        customerServiceIMPL.creditPayment(customerUsername, customerOrderId);

        return "Your payment has been successfully completed";

    }

    @PostMapping("/onlinePayment")

    public String onlinePayment(@ModelAttribute CardInformation cardInformation, HttpServletRequest request) {

        if (cardInformation.getCaptcha().equals(request.getSession().getAttribute("captcha"))) {
            customerServiceIMPL.onlinePayment(cardInformation);
            return "Your payment has been successfully completed";
        } else {
            throw new InvalidInputException("please enter correct captcha");
        }

    }

    @PostMapping("/customerRegisterAReview")
    public String customerRegisterAReview(@RequestBody ReviewDto reviewDto) {

        Review review = ReviewMapper.INSTANCE.dtoToModel(reviewDto);
        CustomerOrder customerOrder = customerOrderServiceIMPL.getCustomerOrderById(reviewDto.getCustomerOrderId());
        Offers offers = offersServiceIMPL.getOffersById(reviewDto.getOffersId());
        review.setOffers(offers);

        review.setCustomerOrder(customerOrder);

        customerServiceIMPL.customerRegisterAReview(review);

        return "your review register";
    }
}
