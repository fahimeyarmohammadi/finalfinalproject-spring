package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer) throws InvalidInputException;

    void changPassword(String username, String repeatNewPassword, String newPassword) throws InvalidInputException;

    Customer signIn(String username, String password) throws NotFoundException, InvalidInputException;

    void customerGetOrder(CustomerOrder order,String username) throws InvalidInputException, NotFoundException;

    List<BaseService> getAllBaseService();

    List<SubService> getAllSubServiceInBaseService(String baseServiceName) throws NotFoundException;

    List<CustomerOrder>GetOrdersWaitingExpertSelection(String username) throws NotFoundException;


}
