package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.SubServiceDto;
import ir.maktab.finalprojectspring.data.model.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubServiceMapper {

    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);

    @Mapping(source = "baseServiceDto", target = "baseService")
    SubService dtoToModel(SubServiceDto subServiceDto);

    SubServiceDto objToDto(SubService subService);

    List<SubServiceDto> listToDtoList(List<SubService> subServiceList);
}
