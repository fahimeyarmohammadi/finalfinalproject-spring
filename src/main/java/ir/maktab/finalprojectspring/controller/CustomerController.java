package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.CustomerChangePasswordDto;
import ir.maktab.finalprojectspring.data.dto.CustomerDto;
import ir.maktab.finalprojectspring.data.dto.CustomerLogInDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.mapper.CustomerMapper;
import ir.maktab.finalprojectspring.service.CustomerServiceIMPL;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceIMPL customerServiceIMPL;

    public CustomerController(CustomerServiceIMPL customerServiceIMPL) {
        this.customerServiceIMPL = customerServiceIMPL;
    }

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


}
