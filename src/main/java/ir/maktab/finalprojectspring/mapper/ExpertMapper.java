package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.model.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert dtoToModel(ExpertDto expertDto);
}
