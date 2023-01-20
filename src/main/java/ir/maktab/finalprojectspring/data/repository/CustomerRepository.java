package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
