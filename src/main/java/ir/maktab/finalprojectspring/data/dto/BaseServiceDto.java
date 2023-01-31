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
    @Pattern(regexp = "^[a-z_A-Z]{3,}$")
    private String name;

}
