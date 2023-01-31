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

public class CustomerLogInDto {

    @NotNull
    @Email(message = "Invalid email! Please enter valid email")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&#^])[A-Za-z0-9@$!%*?&]{8}$",
            message = "password must :8 char, at least one uppercase, one lowercase, one number and one (!@#$%^&*) ")
    private String password;
}
