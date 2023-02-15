package ir.maktab.finalprojectspring.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ReviewDto {

    @NotNull
    @Pattern(regexp = "^[0-5]{1}$")
    int score;

    String comment;

    @NotNull
    Long customerOrderId;

    @NotNull
    Long offersId;
}
