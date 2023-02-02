package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ExpertDto {

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String familyName;

    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8}$",
            message = "password must :8 char, at least one uppercase, one lowercase, and number ")
    private String password;

    private String path;

}
