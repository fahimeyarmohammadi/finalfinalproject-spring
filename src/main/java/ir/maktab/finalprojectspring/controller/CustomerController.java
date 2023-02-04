package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.*;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.mapper.*;
import ir.maktab.finalprojectspring.service.CustomerServiceIMPL;
import ir.maktab.finalprojectspring.service.SubServiceServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceIMPL customerServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;


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

        List<BaseService> baseServiceList=customerServiceIMPL.getAllBaseService();

        List<BaseServiceDto> baseServiceDtoList=new ArrayList<>();

        for (BaseService b:baseServiceList) {

            baseServiceDtoList.add(BaseServiceMapper.INSTANCE.objToDto(b));

        }

        return baseServiceDtoList;

    }

    @GetMapping("/getAllSubServiceInBaseService")
        public List<SubServiceDto> getAllSubServiceInBaseService(@RequestParam String baseServiceName){

        List<SubService>subServiceList=customerServiceIMPL.getAllSubServiceInBaseService(baseServiceName);

        List<SubServiceDto>subServiceDtoList=new ArrayList<>();

        for (SubService s:subServiceList) {

            subServiceDtoList.add(SubServiceMapper.INSTANCE.objToDto(s));

        }

        return subServiceDtoList;

        }

    @PostMapping("/customerGetOrder")

    public String customerGetOrder(@Valid@RequestBody CustomerOrderDto customerOrderDto,@RequestParam String username,@RequestParam String subServiceName) {

        CustomerOrder customerOrder = CustomerOrderMapper.INSTANCE.dtoToModel(customerOrderDto);

        customerOrder.setSubService(subServiceServiceIMPL.getSubServiceByName(subServiceName));

        customerServiceIMPL.customerGetOrder(customerOrder, username);

        return "your order save";
    }
}
