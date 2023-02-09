package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerOrderDto {

    Long id;

    @NotNull
    private Double proposedPrice;

    @NotNull
    private String description;

    @NotNull
    private String preferDate;


    AddressDto addressDto;
}
