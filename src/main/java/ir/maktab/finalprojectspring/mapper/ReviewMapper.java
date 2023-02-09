package ir.maktab.finalprojectspring.mapper;

import ir.maktab.finalprojectspring.data.dto.OffersDto;
import ir.maktab.finalprojectspring.data.dto.ReviewDto;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    Review dtoToModel(ReviewDto reviewDto);
}
