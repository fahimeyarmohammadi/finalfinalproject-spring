package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor


public class BaseServiceDto {

    @NotNull
    private String name;

}
