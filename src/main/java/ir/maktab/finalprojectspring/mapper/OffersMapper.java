package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.OffersDto;
import ir.maktab.finalprojectspring.data.model.Offers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OffersMapper {

    OffersMapper INSTANCE = Mappers.getMapper(OffersMapper.class);

    Offers dtoToModel(OffersDto expertDto);

}
