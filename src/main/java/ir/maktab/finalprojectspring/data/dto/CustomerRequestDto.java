package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomerRequestDto {

    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String familyName;

    @Email(message = "Invalid email! Please enter valid email")
    private String email;

    private String startDate;

    private String endDate;
}
