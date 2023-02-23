package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.repository.OffersRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.util.DateUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OffersServiceIMPL implements OffersService {

    private final OffersRepository offersRepository;

    public void addOffers(Offers offers) {
        if (offers.getOfferPrice() < offers.getCustomerOrder().getSubService().getBasePrice()) {
            throw new InvalidInputException("the offers price must be greater than subService Base price");
        }
        if (DateUtil.dateToLocalDateTime(offers.getStartWork()).isBefore(LocalDateTime.now()))
            throw new InvalidInputException("prefer Date must be after now");

        offersRepository.save(offers);
    }

    public void updateOffers(Offers offers) {
        offersRepository.save(offers);
    }

    public List<Offers> getOffersListOrderedByPrice(CustomerOrder order) {
        return offersRepository.offersListOrderedByPrice(order.getId());
    }

    public List<Offers> getOffersListOrderedByExpertScore(CustomerOrder order) {
        return offersRepository.offersListOrderedByExpertScore(order.getId());
    }

    public Offers getOffersById(Long id) {
        Optional<Offers> optionalOffers = offersRepository.findById(id);
        return optionalOffers.orElseThrow(() -> new NotFoundException("this offers not found"));
    }

    public Offers getOffersByCustomerOrderIdAndOffersCondition(Long customerOrderId) {
        return offersRepository.getOffersByCustomerOrderIdAndOffersCondition(customerOrderId).orElseThrow(() -> new NotFoundException("this offers not found"));
    }

    public List<Offers> getCustomerOrderByCondition(OrderRequestDto request) {
        return offersRepository.findAll(selectByCondition(request));
    }

    static Specification selectByCondition(OrderRequestDto request) {
        return (Specification) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (request.getExpert() != null)
                predicateList.add(cb.equal(root.get("expert"), request.getExpert()));
            if (request.getOrderCondition() != null && request.getOrderCondition().length() != 0)
                predicateList.add(cb.equal(root.get("customerOrder").get("orderCondition"), OrderCondition.valueOf(request.getOrderCondition())));
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

    public List<Offers> getAcceptOffers(String username) {
        return offersRepository.getAcceptOffers(username);
    }
}
