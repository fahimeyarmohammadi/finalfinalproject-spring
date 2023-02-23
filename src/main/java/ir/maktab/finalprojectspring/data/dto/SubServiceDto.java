package ir.maktab.finalprojectspring.data.dto;

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

    private BaseServiceDto baseServiceDto;
}
