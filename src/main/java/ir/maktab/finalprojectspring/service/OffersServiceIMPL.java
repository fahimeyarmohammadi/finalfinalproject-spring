package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.CustomerOrder;
import ir.maktab.finalprojectspring.data.model.Offers;
import ir.maktab.finalprojectspring.data.repository.OffersRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OffersServiceIMPL implements OffersService{

    private final OffersRepository offersRepository;

    public  void addOffers(Offers offers) throws InvalidInputException {

        if(offers.getOfferPrice()<offers.getCustomerOrder().getSubService().getBasePrice()){
            throw new InvalidInputException("the offers price must be greater than subService Base price");
        }

        if (DateUtil.dateToLocalDateTime(offers.getOfferDate()).isBefore(LocalDateTime.now()))
            throw new InvalidInputException("prefer Date must be after now");

        offersRepository.save(offers);

    }

    public List<Offers> getOffersListOrderedByPrice(CustomerOrder order){

        return offersRepository.offersListOrderedByPrice(order.getId());

    }

    public List<Offers> getOffersListOrderedByExpertScore(CustomerOrder order){

        return offersRepository.offersListOrderedByPrice(order.getId());

    }





}