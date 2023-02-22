package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderRequestDto;
import ir.maktab.finalprojectspring.data.dto.CustomerRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor {

    @Query(value = "from CustomerOrder c where c.subService.subName=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SELECTION or c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SUGGESTION)")
    List<CustomerOrder> getAllCustomerOrderInSubService(@Param("subNam") String subName);

    @Override
    Optional<CustomerOrder> findById(Long id);

    @Query(value = "from CustomerOrder c where c.customer.username=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.WAITING_EXPERT_SELECTION)")
    List<CustomerOrder> getAllCustomerOrderWaitingExpertSelection(@Param("username") String username);

    @Query(value = "from CustomerOrder c where c.customer.username=?1 and (c.orderCondition=ir.maktab.finalprojectspring.data.enumeration.OrderCondition.DONE)")
    List<CustomerOrder> getAllCustomerOrderDone(@Param("username") String username);

    @Query(value = "from CustomerOrder c where c.customer.username=?1")
    List<CustomerOrder> getAllCustomerOrder(@Param("username") String username);

    static Specification selectByConditions(OrderRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getCustomer() != null)
                predicateList.add(cb.equal(root.get("customer"), request.getCustomer()));
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0)
                predicateList.add(cb.equal(root.get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

    static Specification selectOrderByManager(CustomerOrderRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0)
                predicateList.add(cb.equal(root.get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));
            if (request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
                predicateList.add(cb.equal(root.get("subService").get("subName"), request.getSubServiceName()));
            if (request.getBaseServiceName() != null && request.getBaseServiceName().length() != 0)
                predicateList.add(cb.equal(root.get("subService").get("baseService").get("name"), request.getBaseServiceName()));
            if(request.getStartDate()!= null && request.getStartDate().length()!= 0 ) {
                try {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("preferDate"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getStartDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
                if(request.getEndDate()!= null && request.getEndDate().length()!= 0){
                    try {
                        predicateList.add(cb.lessThanOrEqualTo(root.get("preferDate"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getEndDate())));
                    } catch (ParseException e) {
                        throw new InvalidInputException("can not convert string to date");
                    }
                }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

}
