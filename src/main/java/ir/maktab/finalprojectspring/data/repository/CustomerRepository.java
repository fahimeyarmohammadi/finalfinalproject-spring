package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.dto.CustomerRequestDto;
import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor {

    Optional<Customer> findByUsername(String username);

    static Specification selectByConditions(CustomerRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getName() != null && request.getName().length() != 0)
                predicateList.add(cb.equal(root.get("name"), request.getName()));
            if (request.getFamilyName() != null && request.getFamilyName().length() != 0)
                predicateList.add(cb.equal(root.get("familyName"), request.getFamilyName()));
            if (request.getEmail() != null && request.getEmail().length() != 0)
                predicateList.add(cb.equal(root.get("email"), request.getEmail()));
            if(request.getStartDate()!= null && request.getStartDate().length()!= 0 ) {
                try {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("date"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getStartDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
            if(request.getEndDate()!= null && request.getEndDate().length()!= 0){
                try {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("date"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getEndDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}

