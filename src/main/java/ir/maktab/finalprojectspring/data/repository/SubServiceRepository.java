package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {
}
