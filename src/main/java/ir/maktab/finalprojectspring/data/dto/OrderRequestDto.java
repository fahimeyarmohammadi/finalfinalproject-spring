package ir.maktab.finalprojectspring.data.dto;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequestDto {
    private Customer customer;
    private Expert expert;
    private String orderCondition;
}
