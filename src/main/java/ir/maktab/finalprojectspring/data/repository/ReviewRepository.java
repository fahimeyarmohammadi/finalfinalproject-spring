package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
