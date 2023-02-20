package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.ExpertRequestDto;
import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.ExpertCondition;
import ir.maktab.finalprojectspring.data.enumeration.UserType;
import ir.maktab.finalprojectspring.data.model.*;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.DateUtil;
import ir.maktab.finalprojectspring.util.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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

    private final ReviewServiceIMPL reviewServiceIMPL;

    private final BCryptPasswordEncoder passwordEncoder;

    public void addExpert(Expert expert) {
        Validation.validateName(expert.getName());
        Validation.validateName(expert.getFamilyName());
        Validation.validateEmail(expert.getEmail());
        Validation.validatePassword(expert.getPassword());
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setUserType(UserType.ROLE_EXPERT);
        expert.setCredit((double) 0);
        expert.setScore(0);
        expert.setExpertCondition(ExpertCondition.NEW);
        expert.setUsername(expert.getEmail());
        try {
            expert.setImage(Validation.validateImage(expert.getPath()));
        } catch (IOException e) {
            throw new InvalidInputException("invalid image file");
        }

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

    public void minusExpertScore(Offers offers){
        if(DateUtil.dateToLocalDateTime(offers.getStartWork()).plus(offers.getDuration()).isBefore(LocalDateTime.now())){
            long lateTime= Duration.between(DateUtil.dateToLocalDateTime(offers.getStartWork()).plus(offers.getDuration()),LocalDateTime.now()).getSeconds();
            int lateTimeHours= (int) (lateTime/3600);
            Expert expert=offers.getExpert();
            int newScore=expert.getScore()-lateTimeHours;
            expert.setScore(newScore);
            expertRepository.save(expert);
            inActiveExpert(expert);
        }
    }

    public void inActiveExpert(Expert expert){
        if(expert.getScore()<0)
            expert.setExpertCondition(ExpertCondition.INACTIVE);

        expertRepository.save(expert);
    }

    public void increaseExpertCredit(Offers offers){
        Expert expert=offers.getExpert();
        Double newCredit=expert.getCredit()+(offers.getOfferPrice()*0.70);
        expert.setCredit(newCredit);
        expertRepository.save(expert);
    }

    public int expertGetOffersScore(Long offersId){
        Review review=reviewServiceIMPL.getOffersScore(offersId);
        return review.getScore();
    }

    public void addReviewToExpertReviewList(Review review){
        Expert expert=review.getOffers().getExpert();
        List<Review>reviewList = expert.getReviewList();
        reviewList.add(review);
        int score=expert.getScore()+review.getScore();
        expert.setScore(score);
        expert.setReviewList(reviewList);
        expertRepository.save(expert);
    }

    public List<Expert> searchAndFilterExpert(ExpertRequestDto request){
        if(request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
            request.setSubService(subServiceServiceIMPL.getSubServiceByName(request.getSubServiceName()));
        return expertRepository.findAll(ExpertRepository.selectByConditions(request));
    }

    public List<Offers> getCustomerOrderByCondition(OrderRequestDto request) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.setExpert(getByUsername(userDetails.getUsername()));
        return offersServiceIMPL.getCustomerOrderByCondition(request);
    }
}
