package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.data.repository.SubServiceRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SubServiceServiceIMPL implements SubServiceService {

    private final SubServiceRepository subServiceRepository;

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    public void addSubService(SubService subService) {
        BaseService baseService=baseServiceServiceIMPL.getBaseServiceByName(subService.getBaseService().getName());
        try {
            subServiceRepository.save(subService);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectExistException("this subService is exist");
        }
    }

    public List<SubService> getAllSubService() {
        return subServiceRepository.findAll();
    }

    public List<SubService> getAllSubServiceInBaseService(String baseServiceName) {
        BaseService baseService=baseServiceServiceIMPL.getBaseServiceByName(baseServiceName);
        return subServiceRepository.findAllByBaseService_Name(baseServiceName);
    }

    public SubService getSubServiceByName(String subName) {
        Optional<SubService> optionalSubService = subServiceRepository.findBySubName(subName);
        return optionalSubService.orElseThrow(() -> new NotFoundException("this subService not found"));
    }


    public void changeSubServiceBasePrice(String subName, Double newPrice) {
        SubService savedSubService = getSubServiceByName(subName);
        savedSubService.setBasePrice(newPrice);
        subServiceRepository.save(savedSubService);
    }

    public void changeSubServiceDescription(String subName, String newDescription) {
        SubService savedSubService = getSubServiceByName(subName);
        savedSubService.setDescription(newDescription);
        subServiceRepository.save(savedSubService);
    }
}
