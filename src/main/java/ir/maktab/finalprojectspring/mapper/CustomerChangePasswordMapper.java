package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.CustomerChangePasswordDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerChangePasswordMapper {

    CustomerChangePasswordMapper INSTANCE = Mappers.getMapper(CustomerChangePasswordMapper.class);

    Customer dtoToModel(CustomerChangePasswordDto changePasswordDto);
}
