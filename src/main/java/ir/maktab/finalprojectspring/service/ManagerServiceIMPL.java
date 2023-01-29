package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.exception.InvalidInputException;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import ir.maktab.finalprojectspring.exception.ObjectExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class ManagerServiceIMPL implements ManagerService {

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final SubServiceServiceIMPL subServiceServiceIMPL;

    private final ExpertServiceIMPL expertServiceIMPL;

    public void addBaseService(BaseService baseService){

        baseServiceServiceIMPL.addBaseService(baseService);

    }

    public void addSubService(SubService subService){

        subServiceServiceIMPL.addSubService(subService);

    }

    public void acceptExpert(String username){

        expertServiceIMPL.acceptExpert(username);

    }

    public void addExpertToSubService(String username, String subServiceName){

        expertServiceIMPL.addSubServiceToExpertList(username, subServiceName);

    }

    public void deleteExpertFromSubService(String username, String subServiceName) {

        expertServiceIMPL.deleteSubServiceFromExpertList(username, subServiceName);


    }

}
