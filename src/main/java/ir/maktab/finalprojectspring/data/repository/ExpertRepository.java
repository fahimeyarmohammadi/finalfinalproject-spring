package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {

    Optional<Expert> findByUsername(String username);

    @Query(value = "From Expert e where e.expertCondition=ir.maktab.finalprojectspring.data.enumeration.ExpertCondition.NEW or e.expertCondition=ir.maktab.finalprojectspring.data.enumeration.ExpertCondition.AWAITING_CONFIRMATION")
    List<Expert> getAllExpertNotAccepted();

    @Query("FROM Expert e WHERE e.verificationCode = ?1")
    Expert findByVerificationCode(String code);

}
