package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Review;

public interface ReviewService {

    void addReview(Review review);

    Review getOffersScore(Long offersId);
}
