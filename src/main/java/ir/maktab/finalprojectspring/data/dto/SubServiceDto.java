package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.BaseService;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SubServiceDto {

    @NotNull
    String subName;

    @NotNull
    BaseService baseServiceDto;

}
