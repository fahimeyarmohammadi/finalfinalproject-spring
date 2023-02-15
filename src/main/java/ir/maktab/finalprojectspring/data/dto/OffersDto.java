package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class OffersDto {

    private Long id;

    @NotNull
    private Double offerPrice;

    @NotNull
    private String startWork;

    @NotNull
    private String duration;

    @NotNull
    private Long customerOrderId;

    @NotNull
    @Email(message = "Invalid email! Please enter valid email")
    private String expertUsername;
}
