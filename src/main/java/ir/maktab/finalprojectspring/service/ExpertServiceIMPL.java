package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.data.enumeration.ExpertCondition;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
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

    public void addExpert(Expert expert) throws IOException {

        Validation.validateName(expert.getName());

        Validation.validateName(expert.getFamilyName());

        Validation.validateEmail(expert.getEmail());

        Validation.validatePassword(expert.getPassword());

        expert.setCredit((double) 0);

        expert.setScore(0);

        expert.setExpertCondition(ExpertCondition.NEW);

        expert.setUsername(expert.getEmail());

        expert.setImage(Validation.validateImage(expert.getPath()));

        try {

            expertRepository.save(expert);

        } catch (DataIntegrityViolationException e) {

            throw new InvalidInputException("Customer already exist with given email:" + expert.getEmail());

        }

    }

    public void changPassword(String username, String repeatNewPassword, String newPassword){

        if (!newPassword.equals(repeatNewPassword))

            throw new InvalidInputException("password and repeatPassword must be equal");

        Optional<Expert> signInExpert = expertRepository.findByUsername(username);

        Expert expert = signInExpert.orElseThrow(() -> new InvalidInputException("Invalid Username"));

        expert.setPassword(newPassword);

        expertRepository.save(expert);

    }

    public Expert signIn(String username, String password){

        Optional<Expert> optionalExpert = expertRepository.findByUsername(username);

        Expert expert = optionalExpert.orElseThrow(() -> new NotFoundException("Invalid Username"));

        if (!expert.getPassword().equals(password))

            throw new NotFoundException("the password is not correct");

        return expert;

    }
    

    public Expert getByUsername(String username){

        Optional<Expert> optionalExpert = expertRepository.findByUsername(username);

       return optionalExpert.orElseThrow(() -> new NotFoundException("Invalid Username"));

    }

    public List<Expert> getExpertNotAccepted() {

        return expertRepository.getAllExpertNotAccepted();

    }

    public void acceptExpert(String username){

        Expert expert = getByUsername(username);

        expert.setExpertCondition(ExpertCondition.ACCEPTED);

        expertRepository.save(expert);

    }

    public void addSubServiceToExpertList(String username, String subServiceName){

        Expert expert = getByUsername(username);

        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);

        List<SubService> subServiceList = expert.getSubServiceList();

        subServiceList.add(subService);

        expertRepository.save(expert);

    }

    @Transactional
    public void deleteSubServiceFromExpertList(String username, String subServiceName){

        Expert expert = getByUsername(username);

        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);

        List<SubService> subServiceList = expert.getSubServiceList();

        if (!subServiceList.contains(subService))

            throw new NotFoundException("expert dont have this subService");

        subServiceList.remove(subService);

        expertRepository.save(expert);

    }

    @Transactional
    public List<CustomerOrder> getAllCustomerOrderInSubService(String userName){

        Expert expert = getByUsername(userName);

        if (! expert.getExpertCondition().equals(ExpertCondition.ACCEPTED))

            throw new InvalidInputException("you dont accepted from manager");

        List<CustomerOrder> customerOrderList = new ArrayList<>();

        for (SubService s : expert.getSubServiceList()) {

            customerOrderList.addAll(customerOrderServiceIMPL.getAllCustomerOrderSInSubService(s.getSubName()));

        }

        return customerOrderList;
    }

    @Transactional
    public void registerOffer(Offers offers){

        if (! offers.getExpert().getExpertCondition().equals(ExpertCondition.ACCEPTED))

            throw new InvalidInputException("you dont accepted from manager");


        offersServiceIMPL.addOffers(offers);

        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(offers.getCustomerOrder().getId());

    }

    public String convertArrayByteToImage(String username) throws IOException {

        Expert expert=getByUsername(username);

        byte[] byteArray=expert.getImage();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);

        BufferedImage newImage = ImageIO.read(inputStream);

        ImageIO.write(newImage, "jpg", new File("outputImage.jpg"));

        return "outputImage.jpg";
    }

}
