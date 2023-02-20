package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.Offers;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> , JpaSpecificationExecutor {

    @Override
    List<Offers> findAll();

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.offerPrice ASC")
    List<Offers> offersListOrderedByPrice(@Param("id") Long id);

    @Query(value = "from Offers o where o.customerOrder.id=?1 ORDER BY o.expert.score DESC ")
    List<Offers> offersListOrderedByExpertScore(@Param("id") Long id);

    @Query(value = "from Offers o where o.customerOrder.id=?1 and o.acceptOffer=true ")
    Optional<Offers> getOffersByCustomerOrderIdAndOffersCondition(@Param("id") Long id);


    static Specification selectByCondition(OrderRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getExpert() != null)
                predicateList.add(cb.equal(root.get("expert"), request.getExpert()));
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0)
                predicateList.add(cb.equal(root.get("customerOrder").get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
