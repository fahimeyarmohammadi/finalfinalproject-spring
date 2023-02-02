package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.CustomerChangePasswordDto;
import ir.maktab.finalprojectspring.data.dto.CustomerDto;
import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.mapper.CustomerMapper;
import ir.maktab.finalprojectspring.mapper.ExpertMapper;
import ir.maktab.finalprojectspring.service.ExpertServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertServiceIMPL expertServiceIMPL;

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

    public String changePassword(@Valid @RequestParam String username,String repeatNewPassword, String newPassword) {

        expertServiceIMPL.changPassword(username, repeatNewPassword, newPassword);

        return "your password changed!";
    }
}

