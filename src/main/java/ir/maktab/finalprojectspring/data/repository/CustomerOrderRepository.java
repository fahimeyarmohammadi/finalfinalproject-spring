package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {
}
