package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.UserType;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.ManagerRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ManagerServiceIMPL implements ManagerService {

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final ExpertServiceIMPL expertServiceIMPL;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ManagerRepository managerRepository;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;

    private final CustomerServiceIMPL customerServiceIMPL;

    @PostConstruct
    public void init(){
        Manager manager=new Manager();
        manager.setUsername("manager");
        manager.setPassword("1234");
        manager.setUserType(UserType.ROLE_MANAGER);
        this.addManager(manager);
    }

    public void addManager(Manager manager){
        manager.setUserType(UserType.ROLE_MANAGER);
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        managerRepository.save(manager);
    }

    public Manager findByUsername(String username){
        Optional<Manager> optionalManager = managerRepository.findByUsername(username);
        return optionalManager.orElseThrow(() -> new NotFoundException("Invalid Username"));
    }

    public void addBaseService(BaseService baseService) {
        baseServiceServiceIMPL.addBaseService(baseService);
    }

    public void addSubService(SubService subService) {
        subServiceServiceIMPL.addSubService(subService);
    }

    public List<BaseService> getAllBaseService()
    {
        return baseServiceServiceIMPL.getAllBaseService();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) {
        return subServiceServiceIMPL.getAllSubServiceInBaseService(baseServiceName);
    }

    public void updateSubServiceDescription(String subName, String newDescription) {
        subServiceServiceIMPL.changeSubServiceDescription(subName, newDescription);
    }

    public void updateSubServicePrice(String subName, Double newPrice) {
        subServiceServiceIMPL.changeSubServiceBasePrice(subName, newPrice);
    }

    public List<Expert> getExpertNotAccepted() {
        return expertServiceIMPL.getExpertNotAccepted();
    }

    public void acceptExpert(String username) {
        expertServiceIMPL.acceptExpert(username);
    }

    public void addExpertToSubService(String username, String subServiceName) {
        expertServiceIMPL.addSubServiceToExpertList(username, subServiceName);
    }

    public void deleteExpertFromSubService(String username, String subServiceName) {
        expertServiceIMPL.deleteSubServiceFromExpertList(username, subServiceName);
    }

    public List<CustomerOrder> getCustomerOrderByManager(CustomerOrderRequestDto request){
        return customerOrderServiceIMPL.getCustomerOrderByManager(request);
    }
    public List<CustomerOrder> getAllCustomerOrders(String username){
        Customer customer=customerServiceIMPL.getByUsername(username);
        return customerOrderServiceIMPL.getAllCustomerOrder(username);
    }
}
