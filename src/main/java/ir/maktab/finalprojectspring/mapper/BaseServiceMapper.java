package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.BaseServiceDto;
import ir.maktab.finalprojectspring.data.model.BaseService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BaseServiceMapper {

    BaseServiceMapper INSTANCE = Mappers.getMapper(BaseServiceMapper.class);

    BaseService dtoToModel(BaseServiceDto baseServiceDto);

    BaseServiceDto objToDto(BaseService baseService);

    List<BaseServiceDto> listToDtoList(List<BaseService> baseServiceList);
}

