package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.SubService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpertRequestDto {

    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String familyName;

    @Email(message = "Invalid email! Please enter valid email")
    private String email;

    private String minScore;

    private String maxScore;

    private String score;

    private String subServiceName;

    private SubService subService;

    private String startDate;

    private String endDate;

    private String customerOrderMinNumber;

    private String customerOrderMaxNumber;
}
