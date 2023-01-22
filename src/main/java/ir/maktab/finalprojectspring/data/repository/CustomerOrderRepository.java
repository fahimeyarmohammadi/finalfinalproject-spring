package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query(value = "from CustomerOrder c where c.subService.subName=:subName and (c.ordercondition=:WAITING_EXPERT_SUGGESTION or c.ordercondition=:WAITING_EXPERT_SELECTION)")
    List<CustomerOrder> getAllCustomerOrderInSubService(@Param("subNam") String subName);

    @Modifying
    @Query(value = "update CustomerOrder  c set c.ordercondition=:WAITING_EXPERT_SELECTION where c.id=:id")
    void changeCustomerOrderConditionToWaitingExpertSelection(Long id);

    @Modifying
    @Query(value = "update CustomerOrder  c set c.ordercondition=:WAITING_FOR_EXPERT_COMING where c.id=:id")
    void changeCustomerOrderConditionToWaitingForExpertComing(Long id);

    @Modifying
    @Query(value = "update CustomerOrder  c set c.ordercondition=:STARTED where c.id=:id")
    void changeCustomerOrderConditionToStarted(Long id);

    @Modifying
    @Query(value = "update CustomerOrder  c set c.ordercondition=:DONE where c.id=:id")
    void changeCustomerOrderConditionToDone(Long id);

}
