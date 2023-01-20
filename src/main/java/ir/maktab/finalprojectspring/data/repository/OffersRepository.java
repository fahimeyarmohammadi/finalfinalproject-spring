package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<Offers,Long> {
}
