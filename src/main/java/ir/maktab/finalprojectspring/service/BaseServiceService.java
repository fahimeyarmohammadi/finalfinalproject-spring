package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;

import java.util.List;
import java.util.Optional;

public interface BaseServiceService {

    void addBaseService(BaseService baseService);

    List<BaseService> getAllBaseService();

    Optional<BaseService> getBaseServiceByName(String baseServiceName);
}
