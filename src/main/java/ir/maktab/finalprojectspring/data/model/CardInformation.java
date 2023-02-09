package ir.maktab.finalprojectspring.data.model;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CardInformation {

    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv2;

    private String customerOrderId;

    private String captcha;

}
