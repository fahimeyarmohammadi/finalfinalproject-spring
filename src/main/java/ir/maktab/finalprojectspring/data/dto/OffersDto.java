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

    @NotNull
    Double offerPrice;

    @NotNull
    Date startWork;

    @NotNull
    ExpertDto expert;

    @NotNull
    Duration duration;

    @NotNull
    CustomerOrderDto customerOrder;

    boolean acceptOffer;
}
