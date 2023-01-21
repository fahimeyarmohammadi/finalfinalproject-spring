package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceIMPL implements ManagerService {

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final ExpertServiceIMPL expertServiceIMPL;

    public void addBaseService(BaseService baseService) throws ObjectExistException {

        baseServiceServiceIMPL.addBaseService(baseService);

    }

    public void addSubService(SubService subService) throws ObjectExistException, NotFoundException {

        subServiceServiceIMPL.addSubService(subService);

    }

    public void acceptExpert(String username) throws NotFoundException {

        expertServiceIMPL.acceptExpert(username);

    }

    public void addExpertToSubService(String username, String subServiceName) throws NotFoundException {

        expertServiceIMPL.addSubServiceToExpertList(username, subServiceName);

    }

    public void deleteExpertFromSubService(String username, String subServiceName) throws InvalidInputException {


    }

}
