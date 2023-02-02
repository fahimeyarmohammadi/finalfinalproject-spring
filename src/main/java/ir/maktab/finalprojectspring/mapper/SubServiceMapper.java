package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.SubServiceDto;
import ir.maktab.finalprojectspring.data.model.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubServiceMapper {

    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);

    SubService dtoToModel(SubServiceDto subServiceDto);

    SubServiceDto objToDto(SubService subService);

}
