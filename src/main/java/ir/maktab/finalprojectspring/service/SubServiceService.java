package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;

import java.util.List;

public interface SubServiceService {

    void addSubService(SubService subService) throws NotFoundException, ObjectExistException;

    List<SubService> getAllSubService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName) throws NotFoundException;

    SubService getSubServiceByName(String subName) throws NotFoundException;

    void changeSubServiceBasePrice(String subName, Double newPrice) throws InvalidInputException;

    void changeSubServiceDescription(String subName, String newDescription) throws InvalidInputException;


}
