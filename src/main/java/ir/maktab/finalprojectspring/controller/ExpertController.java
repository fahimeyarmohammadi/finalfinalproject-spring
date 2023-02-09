package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.*;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.mapper.CustomerMapper;
import ir.maktab.finalprojectspring.mapper.CustomerOrderMapper;
import ir.maktab.finalprojectspring.mapper.ExpertMapper;
import ir.maktab.finalprojectspring.mapper.OffersMapper;
import ir.maktab.finalprojectspring.service.CustomerOrderServiceIMPL;
import ir.maktab.finalprojectspring.service.ExpertServiceIMPL;
import ir.maktab.finalprojectspring.service.ReviewServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertServiceIMPL expertServiceIMPL;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;

    private final ReviewServiceIMPL reviewServiceIMPL;

    @PostMapping("/register")
    public String register(@Valid @RequestBody ExpertDto expertDto){

        Expert expert = ExpertMapper.INSTANCE.dtoToModel(expertDto);

        expertServiceIMPL.addExpert(expert);

        return "you register successfully";
    }


    @PostMapping("/logIn")

    public String logIn(@RequestParam String username, @RequestParam String password){

        expertServiceIMPL.signIn(username,password);

        return "ok";
   }

    @PutMapping("/changePassword")

    public String changePassword( @RequestParam String username,String repeatNewPassword, String newPassword) {

        expertServiceIMPL.changPassword(username, repeatNewPassword, newPassword);

        return "your password changed!";
    }

    @GetMapping("/getAllCustomerOrderInSubService")
    public List<CustomerOrderDto> getAllCustomerOrderInSubService(@RequestParam String username){

        List<CustomerOrder> customerOrderList=expertServiceIMPL.getAllCustomerOrderInSubService(username);

        List<CustomerOrderDto> customerOrderDtoList=new ArrayList<>();

        for (CustomerOrder c:customerOrderList) {

            customerOrderDtoList.add(CustomerOrderMapper.INSTANCE.objToDto(c));

        }

        return customerOrderDtoList;

    }

    @PostMapping("/registerOffer")
    public String registerOffer( @RequestBody OffersDto offersDto){

        CustomerOrder customerOrder=customerOrderServiceIMPL.getCustomerOrderById(offersDto.getCustomerOrderId());

        Expert expert=expertServiceIMPL.getByUsername(offersDto.getExpertUsername());

        Offers offers= OffersMapper.INSTANCE.dtoToModel(offersDto);

        offers.setExpert(expert);

        offers.setCustomerOrder(customerOrder);

        expertServiceIMPL.registerOffer(offers);

        return "your offer register";

    }

    @GetMapping("/expertGetOffersScore")
    public String expertGetOffersScore(@RequestParam Long offersId ){

        return "your score is : " + expertServiceIMPL.expertGetOffersScore(offersId);
    }
}

