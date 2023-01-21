package ir.maktab.finalprojectspring.data.repository;

import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Optional<Expert> findByUsername(String username);

    @Query(value = "From Expert e where e.expertcondition=:NEW or e.expertcondition=:AWAITINGCONFIRMATION")
    List<Expert> getAllExpertNotAccepted();

    @Modifying
    @Query(value="update Expert e set e.expertcondition=:ACCEPTED where e.username=:username" )
    void acceptExpert(@Param("username") String username);

    @Modifying
    @Query(value="update Expert e SET e.password =:password WHERE e.username =:username")
    void updateExpertPassword(@Param("password") String password, @Param("username") String Username);

    @Modifying
    @Query(value = "update Expert e set e.subServiceList=:subServiceList where e.username=:username")
    void updateExpertSubServiceList(@Param("subServiceList") List<SubService> subServiceList , @Param("username") String username);
}
