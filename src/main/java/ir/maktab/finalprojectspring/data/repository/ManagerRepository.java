package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
