package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUsername(String username);

    @Modifying
    @Query("update Customer c SET c.password =:password WHERE c.username =:username")
    void updateCustomerPassword(@Param("password") String password, @Param("username") String Username);


}
