package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.OffersDto;
import ir.maktab.finalprojectspring.data.model.Offers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OffersMapper {

    OffersMapper INSTANCE = Mappers.getMapper(OffersMapper.class);

    @Mapping(source = "startWork", target = "startWork", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Offers dtoToModel(OffersDto expertDto);

}
