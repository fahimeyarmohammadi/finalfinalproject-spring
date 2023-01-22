package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.exception.InvalidInputException;

public interface CustomerOrderService {

    void addOrder(CustomerOrder order) throws InvalidInputException;

}
