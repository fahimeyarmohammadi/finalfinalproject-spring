package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.AddressDto;
import ir.maktab.finalprojectspring.data.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address dtoToModel(AddressDto addressDto);

    AddressDto objToDto(Address address);
}
