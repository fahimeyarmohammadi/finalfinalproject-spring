package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.BaseServiceDto;
import ir.maktab.finalprojectspring.data.dto.ExpertDto;
import ir.maktab.finalprojectspring.data.dto.ExpertViewDto;
import ir.maktab.finalprojectspring.data.dto.SubServiceDto;
import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.mapper.BaseServiceMapper;
import ir.maktab.finalprojectspring.mapper.ExpertViewMapper;
import ir.maktab.finalprojectspring.mapper.SubServiceMapper;
import ir.maktab.finalprojectspring.service.BaseServiceServiceIMPL;
import ir.maktab.finalprojectspring.service.ManagerServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerServiceIMPL managerServiceIMPL;

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    @PostMapping("/addBaseService")
    public String addBaseService(@Valid @RequestBody BaseServiceDto baseServiceDto) {

        BaseService baseService = BaseServiceMapper.INSTANCE.dtoToModel(baseServiceDto);

        managerServiceIMPL.addBaseService(baseService);

        return "baseService added";

    }

    @PostMapping("/addSubService")

    public String addSubService(@Valid @RequestBody SubServiceDto subServiceDto, @RequestParam String baseServiceName) {

        SubService subService = SubServiceMapper.INSTANCE.dtoToModel(subServiceDto);

        BaseService baseService=baseServiceServiceIMPL.getBaseServiceByName(baseServiceName);

        subService.setBaseService(baseService);

        managerServiceIMPL.addSubService(subService);

        return "subService added";

    }
    @GetMapping("/getExpertNotAccepted")

    public List<ExpertViewDto> getExpertNotAccepted(){

        List<Expert> expertList= managerServiceIMPL.getExpertNotAccepted();

        List<ExpertViewDto> expertViewDtoList=new ArrayList<>();

        for (Expert e:expertList) {

            expertViewDtoList.add(ExpertViewMapper.INSTANCE.objToDto(e));

        }

        return expertViewDtoList;

    }

    @PutMapping("/acceptExpert")
    public String acceptExpert(@RequestParam String username){

        managerServiceIMPL.acceptExpert(username);

        return "expert accepted";

    }

    @PostMapping("/addExpertToSubService")

    public String addExpertToSubService(@RequestParam String username,@RequestParam String subServiceName){

        managerServiceIMPL.addExpertToSubService(username,subServiceName);

        return "expert add to subService list";
    }

    @PostMapping("/deleteExpertFromSubService")

    public String deleteExpertFromSubService(@RequestParam String username,@RequestParam String subServiceName){

        managerServiceIMPL.deleteExpertFromSubService(username,subServiceName);

        return "expert deleted from subService";

    }
    @GetMapping("/getAllBaseServiceList")

    public List<BaseServiceDto> getAllBaseServiceList(){

        List<BaseService> baseServiceList=managerServiceIMPL.getAllBaseService();

        List<BaseServiceDto>baseServiceDtoList=new ArrayList<>();

        for (BaseService b:baseServiceList) {

            baseServiceDtoList.add(BaseServiceMapper.INSTANCE.objToDto(b));

        }

        return baseServiceDtoList;
    }

    @GetMapping("/getAllSubServiceInBaseService")
    public List<SubServiceDto> getAllSubServiceInBaseService(@RequestParam String baseServiceName){

       List<SubService>subServiceList= managerServiceIMPL.getAllSubServiceInBaseService(baseServiceName);

       List<SubServiceDto>subServiceDtoList=new ArrayList<>();

        for (SubService s:subServiceList) {

            subServiceDtoList.add(SubServiceMapper.INSTANCE.objToDto(s));

        }

        return subServiceDtoList;
    }

    @PutMapping("/updateSubServiceDescription")

    public String updateSubServiceDescription(@RequestParam String subName,@RequestParam String newDescription){

        managerServiceIMPL.updateSubServiceDescription(subName,newDescription);

        return "subService description update";
    }

    @PutMapping("/updateSubServicePrice")
    public String updateSubServicePrice(@RequestParam String subName,@RequestParam Double newPrice){

        managerServiceIMPL.updateSubServicePrice(subName,newPrice);

        return "subService price update";
    }

}
