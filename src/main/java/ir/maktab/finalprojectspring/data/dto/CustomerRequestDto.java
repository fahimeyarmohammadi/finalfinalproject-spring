package ir.maktab.finalprojectspring.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomerRequestDto {
    private String name;
    private String familyName;
    private String email;
    private Double maxCredit;
    private Double minCredit;
}
