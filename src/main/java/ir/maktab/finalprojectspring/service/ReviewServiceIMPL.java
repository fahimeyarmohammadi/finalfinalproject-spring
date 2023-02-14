package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.model.Review;
import ir.maktab.finalprojectspring.data.repository.ReviewRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceIMPL implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final OffersServiceIMPL offersServiceIMPL;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;

    public void addReview(Review review) {
        Offers offers=offersServiceIMPL.getOffersById(review.getOffers().getId());
        CustomerOrder customerOrder=customerOrderServiceIMPL.getCustomerOrderById(review.getCustomerOrder().getId());
        reviewRepository.save(review);
    }

    public Review getOffersScore(Long offersId) {
        return reviewRepository.getOffersScore(offersId).orElseThrow(() -> new NotFoundException("Invalid offers id"));
    }
}
