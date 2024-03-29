package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Long>, JpaSpecificationExecutor<Offers> {

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.offerPrice ASC")
    List<Offers> offersListOrderedByPrice(@Param("id") Long id);

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.expert.score DESC ")
    List<Offers> offersListOrderedByExpertScore(@Param("id") Long id);

    @Query(value = "from Offers o where o.customerOrder.id=?1 and o.acceptOffer=true ")
    Optional<Offers> getOffersByCustomerOrderIdAndOffersCondition(@Param("id") Long id);

    @Query(value = "from Offers o where o.expert.username=?1 and o.acceptOffer=true ")
    List<Offers> getAcceptOffers(@Param("username") String username);
}
