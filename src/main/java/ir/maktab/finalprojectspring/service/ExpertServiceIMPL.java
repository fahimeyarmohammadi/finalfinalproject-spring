package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.enumeration.ExpertCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertServiceIMPL implements ExpertService {

    private final ExpertRepository expertRepository;

    public void addExpert(Expert expert, String imagePath) throws InvalidInputException, IOException {

        Validation.validateName(expert.getName());
        Validation.validateName(expert.getFamilyName());
        Validation.validateEmail(expert.getEmail());
        Validation.validatePassword(expert.getPassword());
        expert.setCredit((double) 0);
        expert.setScore(0);
        expert.setExpertcondition(ExpertCondition.NEW);
        expert.setUsername(expert.getEmail());
        expert.setExpertImage(Validation.validateImage(imagePath));
        expertRepository.save(expert);

    }

    public void changPassword(String username, String repeatNewPassword, String newPassword) throws InvalidInputException {

        if (!newPassword.equals(repeatNewPassword))
            throw new InvalidInputException("password and repeatPassword must be equal");

        Optional<Expert> signInExpert = expertRepository.findByUsername(username);

        Expert expert = signInExpert.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        expertRepository.updateExpertPassword(newPassword, username);
    }

    public Expert signIn(String username, String password) throws NotFoundException {

        Optional<Expert> optionalExpert = expertRepository.findByUsername(username);
        Expert expert = optionalExpert.orElseThrow(() -> new NotFoundException("Invalid Username"));
        if (!expert.getPassword().equals(password))
            throw new NotFoundException("the password is not correct");
        return expert;

    }

    public Expert getByUsername(String username) throws NotFoundException {

        Optional<Expert> optionalExpert = expertRepository.findByUsername(username);
        return optionalExpert.orElseThrow(() -> new NotFoundException("Invalid Username"));

    }
}
