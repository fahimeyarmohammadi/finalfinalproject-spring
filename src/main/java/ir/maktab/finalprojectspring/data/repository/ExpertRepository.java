package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {
}
