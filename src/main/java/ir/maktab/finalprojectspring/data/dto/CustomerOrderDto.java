package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.SubService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerOrderDto {

    private Long id;

    @NotNull
    private Double proposedPrice;

    @NotNull
    private String description;

    @NotNull
    Date preferDate;

    @NotNull
    OrderCondition orderCondition;

    @NotNull
    private SubServiceDto subService;


}
