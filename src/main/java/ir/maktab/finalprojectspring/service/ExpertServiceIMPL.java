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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final JavaMailSender mailSender;

    public void addExpert(Expert expert) {
        //   Validation.validateName(expert.getName());
        //  Validation.validateName(expert.getFamilyName());
        //   Validation.validateEmail(expert.getEmail());
        // Validation.validatePassword(expert.getPassword());
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setUserType(UserType.ROLE_EXPERT);
        expert.setCredit((double) 0);
        expert.setScore(0);
        expert.setExpertCondition(ExpertCondition.NEW);
        expert.setUsername(expert.getEmail());
//        try {
//            expert.setImage(Validation.validateImage(expert.getPath()));
//        } catch (IOException e) {
//            throw new InvalidInputException("invalid image file");
//        }
        Validation.imageValidation(expert.getImage());
        String randomCode = RandomString.make(64);
        expert.setVerificationCode(randomCode);
        expert.setEnabled(false);
        try {
            expertRepository.save(expert);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException("Customer already exist with given email:" + expert.getEmail());
        }
        sendVerificationEmail(expert);
    }

    public void sendVerificationEmail(Expert expert) {
        String toAddress = expert.getEmail();
        String siteURL = "http://localhost:8080/expert";
        String fromAddress = "fahi.yari@gmail.com";
        String senderName = "fahime";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress, senderName);
        } catch (MessagingException e) {
            throw new InvalidInputException("MessaginExceptio");
        } catch (UnsupportedEncodingException e) {
            throw new InvalidInputException("UnsupportedEncodingException");
        }
        try {
            helper.setTo(toAddress);
        } catch (MessagingException e) {
            throw new InvalidInputException("MessaginExceptio");
        }
        try {
            helper.setSubject(subject);
        } catch (MessagingException e) {
            throw new InvalidInputException("MessaginExceptio");
        }
        content = content.replace("[[name]]", expert.getName());
        String verifyURL = siteURL + "/verify?code=" + expert.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        try {
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new InvalidInputException("MessaginExceptio");
        }
        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        Expert expert = expertRepository.findByVerificationCode(verificationCode);
        if (expert == null || expert.isEnabled()) {
            return false;
        } else {
            expert.setVerificationCode(null);
            expert.setEnabled(true);
            expert.setExpertCondition(ExpertCondition.AWAITING_CONFIRMATION);
            expertRepository.save(expert);
            return true;
        }
    }

    public void changPassword(String username, String repeatNewPassword, String newPassword) {
        if (!newPassword.equals(repeatNewPassword))
            throw new InvalidInputException("password and repeatPassword must be equal");
        Optional<Expert> signInExpert = expertRepository.findByUsername(username);
        Expert expert = signInExpert.orElseThrow(() -> new InvalidInputException("Invalid Username"));
        expert.setPassword(passwordEncoder.encode(newPassword));
        expertRepository.save(expert);
    }

    public Expert getByUsername(String username) {
        Optional<Expert> optionalExpert = expertRepository.findByUsername(username);
        return optionalExpert.orElseThrow(() -> new NotFoundException("Invalid Username"));
    }

    public List<Expert> getExpertNotAccepted() {
        return expertRepository.getAllExpertNotAccepted();
    }

    public void acceptExpert(String username) {
        Expert expert = getByUsername(username);
        expert.setExpertCondition(ExpertCondition.ACCEPTED);
        expertRepository.save(expert);
    }

    public void addSubServiceToExpertList(String username, String subServiceName) {
        Expert expert = getByUsername(username);
        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);
        List<SubService> subServiceList = expert.getSubServiceList();
        subServiceList.add(subService);
        expertRepository.save(expert);
    }

    @Transactional
    public void deleteSubServiceFromExpertList(String username, String subServiceName) {
        Expert expert = getByUsername(username);
        SubService subService = subServiceServiceIMPL.getSubServiceByName(subServiceName);
        List<SubService> subServiceList = expert.getSubServiceList();
        if (!subServiceList.contains(subService))
            throw new NotFoundException("expert dont have this subService");

        subServiceList.remove(subService);
        expertRepository.save(expert);
    }

    @Transactional
    public List<CustomerOrder> getAllCustomerOrderInSubService(String userName) {
        Expert expert = getByUsername(userName);
        if (!expert.getExpertCondition().equals(ExpertCondition.ACCEPTED))
            throw new InvalidInputException("you dont accepted from manager");

        List<CustomerOrder> customerOrderList = new ArrayList<>();
        for (SubService s : expert.getSubServiceList()) {
            customerOrderList.addAll(customerOrderServiceIMPL.getAllCustomerOrderSInSubService(s.getSubName()));
        }
        return customerOrderList;
    }

    @Transactional
    public void registerOffer(Offers offers, Expert expert, CustomerOrder customerOrder) {
        offers.setExpert(expert);
        if (!offers.getExpert().getExpertCondition().equals(ExpertCondition.ACCEPTED))
            throw new InvalidInputException("you dont accepted from manager");
        offers.setCustomerOrder(customerOrder);
        offersServiceIMPL.addOffers(offers);
        customerOrderServiceIMPL.changeCustomerOrderConditionToWaitingForExpertSelection(offers.getCustomerOrder().getId());
    }

    public String convertArrayByteToImage(String username) throws IOException {
        Expert expert = getByUsername(username);
        byte[] byteArray = expert.getImage();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedImage newImage = ImageIO.read(inputStream);
        ImageIO.write(newImage, "jpg", new File("outputImage.jpg"));
        return "outputImage.jpg";
    }

    public void minusExpertScore(Offers offers) {
        if (DateUtil.dateToLocalDateTime(offers.getStartWork()).plus(offers.getDuration()).isBefore(LocalDateTime.now())) {
            long lateTime = Duration.between(DateUtil.dateToLocalDateTime(offers.getStartWork()).plus(offers.getDuration()), LocalDateTime.now()).getSeconds();
            int lateTimeHours = (int) (lateTime / 3600);
            Expert expert = offers.getExpert();
            int newScore = expert.getScore() - lateTimeHours;
            expert.setScore(newScore);
            expertRepository.save(expert);
            inActiveExpert(expert);
        }
    }

    public void inActiveExpert(Expert expert) {
        if (expert.getScore() < 0)
            expert.setExpertCondition(ExpertCondition.INACTIVE);
        expertRepository.save(expert);
    }

    public void increaseExpertCredit(Offers offers) {
        Expert expert = offers.getExpert();
        Double newCredit = expert.getCredit() + (offers.getOfferPrice() * 0.70);
        expert.setCredit(newCredit);
        expertRepository.save(expert);
    }

    public int expertGetOffersScore(Long offersId) {
        Review review = reviewServiceIMPL.getOffersScore(offersId);
        return review.getScore();
    }

    public void addReviewToExpertReviewList(Review review) {
        Expert expert = review.getOffers().getExpert();
        List<Review> reviewList = expert.getReviewList();
        reviewList.add(review);
        int score = expert.getScore() + review.getScore();
        expert.setScore(score);
        expert.setReviewList(reviewList);
        expertRepository.save(expert);
    }

    public List<Expert> searchAndFilterExpert(ExpertRequestDto request) {
        if (request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
            request.setSubService(subServiceServiceIMPL.getSubServiceByName(request.getSubServiceName()));
        return expertRepository.findAll(selectByConditions(request));
    }

    public Specification<Expert> selectByConditions(ExpertRequestDto request) {
        return (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getName() != null && request.getName().length() != 0)
                predicateList.add(cb.equal(root.get("name"), request.getName()));
            if (request.getFamilyName() != null && request.getFamilyName().length() != 0)
                predicateList.add(cb.equal(root.get("familyName"), request.getFamilyName()));
            if (request.getEmail() != null && request.getEmail().length() != 0)
                predicateList.add(cb.equal(root.get("email"), request.getEmail()));
            if (request.getScore() != null && request.getScore().length() != 0)
                predicateList.add(cb.equal(root.get("score"), Integer.parseInt(request.getScore())));
            if (request.getMinScore() != null && request.getMinScore().length() != 0)
                predicateList.add(cb.greaterThanOrEqualTo(root.get("score"), Integer.parseInt(request.getMinScore())));
            if (request.getMaxScore() != null && request.getMaxScore().length() != 0)
                predicateList.add(cb.lessThanOrEqualTo(root.get("score"), Integer.parseInt(request.getMaxScore())));
            if (request.getSubServiceName() != null && request.getSubServiceName().length() != 0)
                predicateList.add(cb.isMember(request.getSubService(), root.get("subServiceList")));
            if (request.getStartDate() != null && request.getStartDate().length() != 0) {
                try {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("date"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getStartDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
            if (request.getEndDate() != null && request.getEndDate().length() != 0) {
                try {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("date"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getEndDate())));
                } catch (ParseException e) {
                    throw new InvalidInputException("can not convert string to date");
                }
            }
            if (request.getCustomerOrderMinNumber() != null && request.getCustomerOrderMinNumber().length() != 0)
                predicateList.add(cb.greaterThanOrEqualTo(root.get("customerOrderNumber"), Integer.valueOf(request.getCustomerOrderMinNumber())));
            if (request.getCustomerOrderMaxNumber() != null && request.getCustomerOrderMaxNumber().length() != 0)
                predicateList.add(cb.lessThanOrEqualTo(root.get("customerOrderNumber"), request.getCustomerOrderMaxNumber()));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

    public List<Offers> getCustomerOrderByCondition(OrderRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.setExpert(getByUsername(userDetails.getUsername()));
        return offersServiceIMPL.getCustomerOrderByCondition(request);
    }

    public void updateExpert(Expert expert) {
        expertRepository.save(expert);
    }
}
