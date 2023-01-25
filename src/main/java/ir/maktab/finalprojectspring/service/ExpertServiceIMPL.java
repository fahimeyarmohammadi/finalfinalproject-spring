package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.data.model.enumeration.ExpertCondition;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertServiceIMPL implements ExpertService {

    private final ExpertRepository expertRepository;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final CustomerOrderServiceIMPL customerOrderServiceIMPL;

    private final OffersServiceIMPL offersServiceIMPL;

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

        try {
            expertRepository.save(expert);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException("Customer already exist with given email:" + expert.getEmail());

        }


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

    public List<Expert> getExpertNotAccepted() {

        return expertRepository.getAllExpertNotAccepted();

    }

    public void acceptExpert(String username) throws NotFoundException {

        Expert expert = getByUsername(username);
        expertRepository.acceptExpert(username);

    }

    public void addSubServiceToExpertList(String username, String subServiceName) throws NotFoundException {

        Expert expert = getByUsername(username);
        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);
        List<SubService> subServiceList = expert.getSubServiceList();
        subServiceList.add(subService);
        expertRepository.updateExpertSubServiceList(subServiceList, username);

    }

    public void deleteSubServiceFromExpertList(String username, String subServiceName) throws NotFoundException {

        Expert expert = getByUsername(username);
        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);
        List<SubService> subServiceList = expert.getSubServiceList();
        if (subServiceList.contains(subService))
            subServiceList.remove(subService);
        else
            throw new NotFoundException("expert dont have this subService");
        expertRepository.updateExpertSubServiceList(subServiceList, username);

    }

    public List<CustomerOrder> getAllCustomerOrderInSubService(String userName) throws NotFoundException {

        Expert expert=getByUsername(userName);

        List<CustomerOrder>customerOrderList=new ArrayList<>();

        for (SubService s:expert.getSubServiceList()) {

            customerOrderList.addAll(customerOrderServiceIMPL.getAllCustomerOrderSInSubService(s.getSubName()));

        }

        return customerOrderList;
    }

    public void registerOffer(Offers offers) throws InvalidInputException {

        offersServiceIMPL.addOffers(offers);

         customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(offers.getCustomerOrder());

    }






}
