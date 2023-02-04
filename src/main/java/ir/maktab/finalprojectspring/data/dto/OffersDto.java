package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.util.Date;

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
    private ExpertDto expert;

    @NotNull
    private Duration duration;

    @NotNull
    private CustomerOrderDto customerOrder;

    private boolean acceptOffer;
}
