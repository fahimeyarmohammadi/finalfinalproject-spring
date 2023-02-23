package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor {

    Optional<Customer> findByUsername(String username);
}

