package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseServiceRepository extends JpaRepository<BaseService,Long> {
}
