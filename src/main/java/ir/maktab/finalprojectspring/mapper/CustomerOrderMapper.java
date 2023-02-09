package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderDto;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerOrderMapper {

    CustomerOrderMapper INSTANCE = Mappers.getMapper(CustomerOrderMapper.class);

    @Mapping(source = "preferDate", target = "preferDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CustomerOrder dtoToModel(CustomerOrderDto customerOrderDto);

    @Mapping(source = "address", target = "addressDto")
    CustomerOrderDto objToDto(CustomerOrder customerOrder);
}
