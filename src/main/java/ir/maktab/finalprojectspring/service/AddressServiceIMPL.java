package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Address;
import ir.maktab.finalprojectspring.data.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AddressServiceIMPL implements AddressService{

    private final AddressRepository addressRepository;

    public void addAddress(Address address){

        addressRepository.save(address);

    }
}
