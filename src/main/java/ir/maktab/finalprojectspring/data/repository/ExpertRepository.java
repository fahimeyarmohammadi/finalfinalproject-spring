package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.dto.ExpertRequestDto;
import ir.maktab.finalprojectspring.data.model.Expert;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor {

    Optional<Expert> findByUsername(String username);

    @Query(value = "From Expert e where e.expertCondition=ir.maktab.finalprojectspring.data.enumeration.ExpertCondition.NEW or e.expertCondition=ir.maktab.finalprojectspring.data.enumeration.ExpertCondition.AWAITING_CONFIRMATION")
    List<Expert> getAllExpertNotAccepted();

    static Specification selectByConditions(ExpertRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getName() != null && request.getName().length() != 0)
                predicateList.add(cb.equal(root.get("name"), request.getName()));
            if (request.getFamilyName() != null && request.getFamilyName().length() != 0)
                predicateList.add(cb.equal(root.get("familyName"), request.getFamilyName()));
            if (request.getEmail() != null && request.getEmail().length() != 0)
                predicateList.add(cb.equal(root.get("email"), request.getEmail()));
            if (request.getScore() != null && request.getScore().length() != 0)
                predicateList.add(cb.equal(root.get("score"), Integer.parseInt(request.getScore())));
            if (request.getMinScore() != null && request.getMinScore().length() != 0)
                predicateList.add(cb.greaterThanOrEqualTo(root.get("score"), Integer.parseInt(request.getMinScore())));
            if (request.getMaxScore() != null && request.getMaxScore().length() != 0)
                predicateList.add(cb.lessThanOrEqualTo(root.get("score"), Integer.parseInt(request.getMaxScore())));
            if (request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
                predicateList.add(cb.isMember(request.getSubService(),root.get("subServiceList")));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
