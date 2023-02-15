package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.repository.BaseServiceRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class BaseServiceServiceIMPL implements BaseServiceService {

    private final BaseServiceRepository baseServiceRepository;

    public void addBaseService(BaseService baseService) {
        try {
            baseServiceRepository.save(baseService);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectExistException("This baseService is exist");
        }
    }

    public List<BaseService> getAllBaseService() {
        return baseServiceRepository.findAll();
    }

    public BaseService getBaseServiceByName(String baseServiceName) {
        return baseServiceRepository.findByName(baseServiceName).orElseThrow(() -> new NotFoundException("Invalid name"));
    }
}
