package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.CustomerDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer dtoToModel(CustomerDto customerDto);

}
