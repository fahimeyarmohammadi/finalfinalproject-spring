package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;

public interface ManagerService {

    void addBaseService(BaseService baseService);

    void addSubService(SubService subService);

    void acceptExpert(String username);

    void addExpertToSubService(String username, String subServiceName);

    void deleteExpertFromSubService(String username, String subServiceName);
}
