package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Optional<SubService> findBySubName(String name);

    List<SubService> findAllByBaseService_Name(String name);

    @Modifying
    @Query("update SubService s SET s.basePrice =:basePrice WHERE s.subName =:subName")
    void updateSubServiceBasePrice(@Param("subName") String subName, @Param("basePrice") Double basePrice);

    @Modifying
    @Query("update SubService s SET s.description =:description WHERE s.subName =:subName")
    void updateSubServiceDescription(@Param("subName") String subName, @Param("description") String description);
}
