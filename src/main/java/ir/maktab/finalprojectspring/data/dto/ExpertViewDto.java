package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.enumeration.ExpertCondition;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ExpertViewDto {

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String familyName;

    @Email
    private String email;

    private ExpertCondition expertCondition;

    private int score;
}
