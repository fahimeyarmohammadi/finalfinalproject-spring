package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.Address;
import ir.maktab.finalprojectspring.data.model.SubService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerOrderDto {

    @NotNull
    private Double proposedPrice;

    @NotNull
    private String description;

    @NotNull
    private Date preferDate;

    @NotNull
    private String subServiceName;

    AddressDto addressDto;
}
