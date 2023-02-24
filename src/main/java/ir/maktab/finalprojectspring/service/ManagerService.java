package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.CustomerOrderRequestDto;
import ir.maktab.finalprojectspring.data.model.*;

import java.util.List;

public interface ManagerService {

    void addManager(Manager manager);

    Manager findByUsername(String username);

    void addBaseService(BaseService baseService);

    void addSubService(SubService subService, BaseService baseService);

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    void updateSubServiceDescription(String subName, String newDescription);

    void updateSubServicePrice(String subName, Double newPrice);

    List<Expert> getExpertNotAccepted();

    void acceptExpert(String username);

    void addExpertToSubService(String username, String subServiceName);

    void deleteExpertFromSubService(String username, String subServiceName);

    List<CustomerOrder> getCustomerOrderByManager(CustomerOrderRequestDto request);

    List<CustomerOrder> getAllCustomerOrders(String username);

    List<CustomerOrder> getExpertAllCustomerOrder(String username);


}
