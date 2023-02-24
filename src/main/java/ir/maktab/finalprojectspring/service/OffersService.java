package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.dto.OrderRequestDto;
import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Offers;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OffersService {

    void addOffers(Offers offers);

    void updateOffers(Offers offers);

    List<Offers> getOffersListOrderedByPrice(CustomerOrder customerOrder);

    List<Offers> getOffersListOrderedByExpertScore(CustomerOrder customerOrder);

    Offers getOffersById(Long id);

    Offers getOffersByCustomerOrderIdAndOffersCondition(Long customerOrderId);

    List<Offers> getCustomerOrderByCondition(OrderRequestDto request);

    Specification<Offers> selectByCondition(OrderRequestDto request);

    List<Offers> getAcceptOffers(String username);
}
