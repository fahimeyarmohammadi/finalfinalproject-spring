package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;

import java.io.IOException;

public interface ExpertService {

    void addExpert(Expert expert, String imagePath) throws InvalidInputException, IOException;

    void changPassword(String username, String repeatNewPassword, String newPassword) throws InvalidInputException;

    Expert signIn(String username, String password) throws NotFoundException;

    Expert getByUsername(String username) throws NotFoundException;

}
