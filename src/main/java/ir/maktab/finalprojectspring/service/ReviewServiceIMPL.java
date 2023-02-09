package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Review;
import ir.maktab.finalprojectspring.data.repository.ReviewRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceIMPL implements ReviewService{

    private final ReviewRepository reviewRepository;

    public void addReview(Review review){

        reviewRepository.save(review);
    }

    public Review getOffersScore(Long offersId){

       return  reviewRepository.getOffersScore(offersId).orElseThrow(() -> new NotFoundException("Invalid offers id"));
    }
}
