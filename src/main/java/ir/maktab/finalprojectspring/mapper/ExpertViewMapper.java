package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.ExpertViewDto;
import ir.maktab.finalprojectspring.data.model.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpertViewMapper {

    ExpertViewMapper INSTANCE = Mappers.getMapper(ExpertViewMapper.class);

    Expert dtoToModel(ExpertViewDto expertViewDto);

    ExpertViewDto objToDto(Expert expert);

    List<ExpertViewDto> listToDtoList(List<Expert> expertList);
}
