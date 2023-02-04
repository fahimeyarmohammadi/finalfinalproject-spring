package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class CustomerOrderDto {

    @NotNull
    private Double proposedPrice;

    @NotNull
    private String description;

    @NotNull
    private String preferDate;


    Address address;
}
