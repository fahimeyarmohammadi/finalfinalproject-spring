package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderDto;
import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.dto.OffersDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.mapper.CustomerOrderMapper;
import ir.maktab.finalprojectspring.mapper.ExpertMapper;
import ir.maktab.finalprojectspring.mapper.OffersMapper;
import ir.maktab.finalprojectspring.service.CustomerOrderServiceIMPL;
import ir.maktab.finalprojectspring.service.ExpertServiceIMPL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertServiceIMPL expertServiceIMPL;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;


    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String register(@Valid@ModelAttribute ExpertDto expertDto,HttpServletRequest request) {
        Expert expert = ExpertMapper.INSTANCE.dtoToModel(expertDto);
        expertServiceIMPL.addExpert(expert,getSiteURL(request));
        return "you register successfully";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (expertServiceIMPL.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PutMapping("/changePassword")
    public String changePassword(@RequestParam String username, String repeatNewPassword, String newPassword) {
        expertServiceIMPL.changPassword(username, repeatNewPassword, newPassword);
        return "your password changed!";
    }

    @GetMapping("/getAllCustomerOrderInSubService")
    public List<CustomerOrderDto> getAllCustomerOrderInSubService(@RequestParam String username) {
        List<CustomerOrder> customerOrderList = expertServiceIMPL.getAllCustomerOrderInSubService(username);
        return CustomerOrderMapper.INSTANCE.listToDtoList(customerOrderList);
    }

    @PostMapping("/registerOffer")
    public String registerOffer(@RequestBody OffersDto offersDto) {
        CustomerOrder customerOrder = customerOrderServiceIMPL.getCustomerOrderById(offersDto.getCustomerOrderId());
        Expert expert = expertServiceIMPL.getByUsername(offersDto.getExpertUsername());
        Offers offers = OffersMapper.INSTANCE.dtoToModel(offersDto);
        expertServiceIMPL.registerOffer(offers,expert,customerOrder);
        return "your offer register";
    }

    @GetMapping("/expertGetOffersScore")
    public String expertGetOffersScore(@RequestParam Long offersId) {
        return "your score is : " + expertServiceIMPL.expertGetOffersScore(offersId);
    }

    @PostMapping("/getCustomerOrderByCondition")
    public List<OffersDto> getCustomerOrderByCondition(@RequestBody OrderRequestDto request) {
        return OffersMapper.INSTANCE.listToDtoList(expertServiceIMPL.getCustomerOrderByCondition(request));
    }
}

