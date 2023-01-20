package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {
    Optional<SubService> findBySubName(String name);
    List<SubService> findAllByBaseServiceEquals(String name);
}
