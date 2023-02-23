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


    @PutMapping("/changePassword")
    public String changePassword(@RequestParam String username, String repeatNewPassword, String newPassword) {
        customerServiceIMPL.changPassword(username, repeatNewPassword, newPassword);
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
        Address address = AddressMapper.INSTANCE.dtoToModel(customerOrderDto.getAddressDto());
        SubService subService=subServiceServiceIMPL.getSubServiceByName(subServiceName);
        customerServiceIMPL.customerGetOrder(customerOrder,username,address,subService);
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

    @GetMapping("/getOffersListOrderedByPrice")
    public List<OffersDto> getOffersListOrderedByPrice(@RequestParam Long orderId) {
        CustomerOrder customerOrder = customerOrderServiceIMPL.getCustomerOrderById(orderId);
        List<Offers> offersList = customerServiceIMPL.getOffersListOrderedByPrice(customerOrder);
        return OffersMapper.INSTANCE.listToDtoList(offersList);
    }

    @GetMapping("/getOffersListOrderedByExpertScore")
    public List<OffersDto> getOffersListOrderedByExpertScore(@RequestParam Long orderId) {
        CustomerOrder customerOrder = customerOrderServiceIMPL.getCustomerOrderById(orderId);
        List<Offers> offersList = customerServiceIMPL.getOffersListOrderedByExpertScore(customerOrder);
        return OffersMapper.INSTANCE.listToDtoList(offersList);
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

    @GetMapping("/getOrdersDone")
    public List<CustomerOrderDto> getOrdersDone(@RequestParam String username) {
        List<CustomerOrder> customerOrderList = customerServiceIMPL.getOrderDone(username);
        return CustomerOrderMapper.INSTANCE.listToDtoList(customerOrderList);
    }

    @PutMapping("/creditPayment")
    public String creditPayment(@RequestParam String customerUsername, @RequestParam Long customerOrderId) {
        customerServiceIMPL.creditPayment(customerUsername, customerOrderId);
        return "Your payment has been successfully completed";
    }

    @PostMapping("/onlinePayment")
    public void onlinePayment(@Valid @ModelAttribute CardInformationDto cardInformation, HttpServletRequest request) {
        if (cardInformation.getCaptcha().equals(request.getSession().getAttribute("captcha"))) {
            customerServiceIMPL.onlinePayment(cardInformation);
        } else {
            throw new InvalidInputException("please enter correct captcha");
        }
    }

    @PostMapping("/customerRegisterAReview")
    public String customerRegisterAReview(@RequestBody ReviewDto reviewDto) {
        Review review = ReviewMapper.INSTANCE.dtoToModel(reviewDto);
        CustomerOrder customerOrder = customerOrderServiceIMPL.getCustomerOrderById(reviewDto.getCustomerOrderId());
        Offers offers = offersServiceIMPL.getOffersById(reviewDto.getOffersId());
        customerServiceIMPL.customerRegisterAReview(review, offers,customerOrder);
        return "your review register";
    }

    @GetMapping("/getCredit")
    public Double customerGetCredit(){
        return customerServiceIMPL.getCredit();
    }

    @PostMapping("/getCustomerOrderByCondition")
    public List<CustomerOrderDto> getCustomerOrderByCondition(@RequestBody OrderRequestDto orderRequestDto){
        return CustomerOrderMapper.INSTANCE.listToDtoList(customerServiceIMPL.getCustomerOrderByCondition(orderRequestDto));
    }

}
