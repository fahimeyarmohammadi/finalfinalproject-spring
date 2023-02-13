package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AddressDto {

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String alley;

    @NotNull
    private String houseNumber;
}
