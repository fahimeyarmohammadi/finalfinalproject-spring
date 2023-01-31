package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.CustomerDto;
import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper( ExpertMapper.class);

    Customer dtoToModel(ExpertDto expertDto);

}
