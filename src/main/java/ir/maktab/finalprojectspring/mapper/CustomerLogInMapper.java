package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.CustomerLogInDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CustomerLogInMapper {

    CustomerLogInMapper INSTANCE = Mappers.getMapper(CustomerLogInMapper.class);

    Customer dtoToModel(CustomerLogInDto logInDto);
}
