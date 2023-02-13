package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.SubService;

import java.util.List;

public interface ManagerService {

    void addBaseService(BaseService baseService);

    void addSubService(SubService subService);

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    void updateSubServiceDescription(String subName, String newDescription);

    void updateSubServicePrice(String subName, Double newPrice);

    List<Expert> getExpertNotAccepted();

    void acceptExpert(String username);

    void addExpertToSubService(String username, String subServiceName);

    void deleteExpertFromSubService(String username, String subServiceName);
}
