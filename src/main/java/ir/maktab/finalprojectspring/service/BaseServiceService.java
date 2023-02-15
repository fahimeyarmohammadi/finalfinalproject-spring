package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;

import java.util.List;

public interface BaseServiceService {

    void addBaseService(BaseService baseService);

    List<BaseService> getAllBaseService();

    BaseService getBaseServiceByName(String baseServiceName);
}
