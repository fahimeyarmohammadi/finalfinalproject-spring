package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query(value = "from CustomerOrder c where c.subService.subName=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SELECTION or c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SUGGESTION)")
    List<CustomerOrder> getAllCustomerOrderInSubService(@Param("subNam") String subName);

    @Override
    Optional<CustomerOrder> findById(Long id);
}
