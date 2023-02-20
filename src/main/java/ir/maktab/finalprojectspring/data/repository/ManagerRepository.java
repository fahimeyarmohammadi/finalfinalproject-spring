package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUsername(String username);
}
