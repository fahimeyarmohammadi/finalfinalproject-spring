package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    Optional<Customer> findByUsername(String username);

    @Query(value = "From Expert e where e.expertcondition=:NEW or e.expertcondition=:AWAITINGCONFIRMATION")
    List<Expert> getAllExpertNotAccepted();

}