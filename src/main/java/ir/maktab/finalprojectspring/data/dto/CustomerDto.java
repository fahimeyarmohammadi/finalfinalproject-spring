package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8}$",
            message = "password must :8 char, at least one uppercase, one lowercase and one number")
    private String password;
}
