package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.BaseServiceDto;
import ir.maktab.finalprojectspring.data.dto.CustomerOrderDto;
import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerOrderMapper {

    CustomerOrderMapper INSTANCE = Mappers.getMapper(CustomerOrderMapper.class);

    CustomerOrder dtoToModel(CustomerOrderDto expertDto);
}
