package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.repository.BaseServiceRepository;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseServiceServiceIMPL implements BaseServiceService{

    private final BaseServiceRepository baseServiceRepository;

    public void addBaseService(BaseService baseService)  {

        try {

            baseServiceRepository.save(baseService);

        } catch (DataIntegrityViolationException e) {

            throw new ObjectExistException("This baseService is exist");

        }

    }
    public List<BaseService> getAllBaseService() {
        return  baseServiceRepository.findAll();
    }

    public Optional<BaseService> getBaseServiceByName(String baseServiceName){

        return baseServiceRepository.findByName(baseServiceName);

    }
}
