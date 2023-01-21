package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.exception.ObjectExistException;

import java.util.List;
import java.util.Optional;

public interface BaseServiceService {

    void addBaseService(BaseService baseService) throws ObjectExistException;

    List<BaseService> getAllBaseService();

    Optional<BaseService> getBaseServiceByName(String baseServiceName);
}
