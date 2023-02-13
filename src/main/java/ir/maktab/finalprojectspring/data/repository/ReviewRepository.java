package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "from Review r where r.offers.id=?1")
    Optional<Review> getOffersScore(@Param("offersId") Long id);
}
