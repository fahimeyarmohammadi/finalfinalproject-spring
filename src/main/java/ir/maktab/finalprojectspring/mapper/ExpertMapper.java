package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.model.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);
//multipartfile--[]byte

    @Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToArrayByte")
    Expert dtoToModel(ExpertDto expertDto);

    @Named("multipartFileToArrayByte")
    static byte[] multipartFileToArrayByte(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }
}
