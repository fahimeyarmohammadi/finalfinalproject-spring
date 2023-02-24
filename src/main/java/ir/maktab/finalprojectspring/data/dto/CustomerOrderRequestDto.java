package ir.maktab.finalprojectspring.data.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerOrderRequestDto {
    private String subServiceName;

    private String baseServiceName;

    private String orderCondition;

    private String startDate;

    private String endDate;
}
