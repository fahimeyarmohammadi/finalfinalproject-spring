package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.offerPrice ASC")

    List<Offers> offersListOrderedByPrice(@Param("id") Long id);

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.expert.score DESC ")

    List<Offers> offersListOrderedByExpertScore(@Param("id") Long id);

}
