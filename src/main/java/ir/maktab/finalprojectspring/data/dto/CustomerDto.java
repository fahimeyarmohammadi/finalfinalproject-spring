package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String familyName;

    @NotNull
    @Email(message = "Invalid email! Please enter valid email")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&#^])[A-Za-z0-9@$!%*?&]{8}$",
            message = "password must :8 char, at least one uppercase, one lowercase, one number and one (!@#$%^&*) ")
    private String password;

    List<CustomerOrderDto> orderList = new ArrayList<>();

}
