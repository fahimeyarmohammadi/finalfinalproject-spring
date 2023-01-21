package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.data.repository.SubServiceRepository;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceServiceIMPL implements SubServiceService {
    private final SubServiceRepository subServiceRepository;
    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    public void addSubService(SubService subService) throws NotFoundException, ObjectExistException {

        if (baseServiceServiceIMPL.getBaseServiceByName(subService.getBaseService().getName()).isEmpty())
            throw new NotFoundException("this baseService is not exist");


        for (SubService s : subServiceRepository.findAllByBaseService_Name(subService.getBaseService().getName())) {
            if (s.getSubName().equals(subService.getSubName()))
                throw new ObjectExistException("this subService is exist");
        }


        subServiceRepository.save(subService);

    }

    public List<SubService> getAllSubService() {
        return subServiceRepository.findAll();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) throws NotFoundException {

        if (baseServiceServiceIMPL.getBaseServiceByName(baseServiceName).isPresent())
            return subServiceRepository.findAllByBaseService_Name(baseServiceName);
        else
            throw new NotFoundException("this baseService is not exist");

    }

    public SubService getSubServiceByName(String subName) throws NotFoundException {

        Optional<SubService> optionalSubService = subServiceRepository.findBySubName(subName);
        if (optionalSubService.isPresent()) return optionalSubService.get();
        else throw new NotFoundException("SubService not sound");

    }


    public void changeSubServiceBasePrice(String subName, Double newPrice) throws InvalidInputException {

        Optional<SubService> optionalSubService = subServiceRepository.findBySubName(subName);

        SubService subService = optionalSubService.orElseThrow(() -> new InvalidInputException("Invalid subService name"));

        subServiceRepository.updateSubServiceBasePrice(subName, newPrice);

    }

    public void changeSubServiceDescription(String subName, String newDescription) throws InvalidInputException {

        Optional<SubService> optionalSubService = subServiceRepository.findBySubName(subName);

        SubService subService = optionalSubService.orElseThrow(() -> new InvalidInputException("Invalid subService name"));

        subServiceRepository.updateSubServiceDescription(subName, newDescription);

    }
}
