package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;

public interface ManagerService {

    void addBaseService(BaseService baseService) throws ObjectExistException;

    void addSubService(SubService subService) throws ObjectExistException, NotFoundException;

    void acceptExpert(String username) throws NotFoundException;

    void addExpertToSubService(String username, String subServiceName) throws NotFoundException ;

    void deleteExpertFromSubService(String username, String subServiceName) throws InvalidInputException;
}
