package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.BaseService;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SubServiceDto {

    @NotNull
    private String subName;

    @NotNull
    private Double basePrice;

    @NotNull
    private String description;

}
