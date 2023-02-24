package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor<CustomerOrder> {

    @Query(value = "from CustomerOrder c where c.subService.subName=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SELECTION or c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SUGGESTION)")
    List<CustomerOrder> getAllCustomerOrderInSubService(@Param("subNam") String subName);

    @Query(value = "from CustomerOrder c where c.customer.username=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SELECTION)")
    List<CustomerOrder> getAllCustomerOrderWaitingExpertSelection(@Param("username") String username);

    @Query(value = "from CustomerOrder c where c.customer.username=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.DONE)")
    List<CustomerOrder> getAllCustomerOrderDone(@Param("username") String username);

    @Query(value = "from CustomerOrder c where c.customer.username=?1")
    List<CustomerOrder> getAllCustomerOrder(@Param("username") String username);
}
