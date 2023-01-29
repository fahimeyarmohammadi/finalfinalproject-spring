package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;

import java.util.List;

public interface SubServiceService {

    void addSubService(SubService subService);

    List<SubService> getAllSubService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName);

    SubService getSubServiceByName(String subName);

    void changeSubServiceBasePrice(String subName, Double newPrice);

    void changeSubServiceDescription(String subName, String newDescription);


}
