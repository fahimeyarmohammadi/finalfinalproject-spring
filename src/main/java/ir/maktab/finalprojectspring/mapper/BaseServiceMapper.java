package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.BaseServiceDto;
import ir.maktab.finalprojectspring.data.model.BaseService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseServiceMapper {

    BaseServiceMapper INSTANCE = Mappers.getMapper(BaseServiceMapper.class);

    BaseService dtoToModel(BaseServiceDto expertDto);

    BaseServiceDto objToDto(BaseService baseService);

}

