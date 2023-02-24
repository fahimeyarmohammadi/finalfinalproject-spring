package ir.maktab.finalprojectspring.controller;

import ir.maktab.finalprojectspring.data.dto.*;
import ir.maktab.finalprojectspring.data.model.BaseService;
import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.SubService;
import ir.maktab.finalprojectspring.mapper.*;
import ir.maktab.finalprojectspring.service.BaseServiceServiceIMPL;
import ir.maktab.finalprojectspring.service.CustomerServiceIMPL;
import ir.maktab.finalprojectspring.service.ExpertServiceIMPL;
import ir.maktab.finalprojectspring.service.ManagerServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerServiceIMPL managerServiceIMPL;

    private final BaseServiceServiceIMPL baseServiceServiceIMPL;

    private final CustomerServiceIMPL customerServiceIMPL;

    private final ExpertServiceIMPL expertServiceIMPL;

    @PostMapping("/addBaseService")
    public String addBaseService(@Valid @RequestBody BaseServiceDto baseServiceDto) {
        BaseService baseService = BaseServiceMapper.INSTANCE.dtoToModel(baseServiceDto);
        managerServiceIMPL.addBaseService(baseService);
        return "baseService added";
    }

    @PostMapping("/addSubService")
    public String addSubService(@Valid @RequestBody SubServiceDto subServiceDto, @RequestParam String baseServiceName) {
        SubService subService = SubServiceMapper.INSTANCE.dtoToModel(subServiceDto);
        BaseService baseService = baseServiceServiceIMPL.getBaseServiceByName(baseServiceName);
        managerServiceIMPL.addSubService(subService, baseService);
        return "subService added";
    }

    @GetMapping("/getExpertNotAccepted")
    public List<ExpertViewDto> getExpertNotAccepted() {
        List<Expert> expertList = managerServiceIMPL.getExpertNotAccepted();
        return ExpertViewMapper.INSTANCE.listToDtoList(expertList);
    }

    @PutMapping("/acceptExpert")
    public String acceptExpert(@RequestParam String username) {
        managerServiceIMPL.acceptExpert(username);
        return "expert accepted";
    }

    @GetMapping("/addExpertToSubService")
    public String addExpertToSubService(@RequestParam String username, @RequestParam String subServiceName) {
        managerServiceIMPL.addExpertToSubService(username, subServiceName);
        return "expert add to subService list";
    }

    @PutMapping("/deleteExpertFromSubService")
    public String deleteExpertFromSubService(@RequestParam String username, @RequestParam String subServiceName) {
        managerServiceIMPL.deleteExpertFromSubService(username, subServiceName);
        return "expert deleted from subService";
    }

    @GetMapping("/getAllBaseServiceList")
    public List<BaseServiceDto> getAllBaseServiceList() {
        List<BaseService> baseServiceList = managerServiceIMPL.getAllBaseService();
        return BaseServiceMapper.INSTANCE.listToDtoList(baseServiceList);
    }

    @GetMapping("/getAllSubServiceInBaseService")
    public List<SubServiceDto> getAllSubServiceInBaseService(@RequestParam String baseServiceName) {
        List<SubService> subServiceList = managerServiceIMPL.getAllSubServiceInBaseService(baseServiceName);
        return SubServiceMapper.INSTANCE.listToDtoList(subServiceList);
    }

    @PutMapping("/updateSubServiceDescription")
    public String updateSubServiceDescription(@RequestParam String subName, @RequestParam String newDescription) {
        managerServiceIMPL.updateSubServiceDescription(subName, newDescription);
        return "subService description update";
    }

    @PutMapping("/updateSubServicePrice")
    public String updateSubServicePrice(@RequestParam String subName, @RequestParam Double newPrice) {
        managerServiceIMPL.updateSubServicePrice(subName, newPrice);
        return "subService price update";
    }

    @PostMapping("/searchAndFilterCustomer")
    public List<CustomerDto> searchAndFilterCustomer(@RequestBody CustomerRequestDto requestDto) {
        List<Customer> customerList = customerServiceIMPL.searchAndFilterCustomer(requestDto);
        return CustomerMapper.INSTANCE.listToDtoList(customerList);
    }

    @PostMapping("/searchAndFilterExpert")
    public List<ExpertViewDto> searchAndFilterExpert(@RequestBody ExpertRequestDto requestDto) {
        List<Expert> expertList = expertServiceIMPL.searchAndFilterExpert(requestDto);
        return ExpertViewMapper.INSTANCE.listToDtoList(expertList);

    }

    @PostMapping("/getCustomerOrderByManager")
    public List<CustomerOrderDto> getCustomerOrderByManager(@RequestBody CustomerOrderRequestDto request) {
        return CustomerOrderMapper.INSTANCE.listToDtoList(managerServiceIMPL.getCustomerOrderByManager(request));
    }

    @GetMapping("/getAllCustomerOrders")
    public List<CustomerOrderDto> getAllCustomerOrders(@RequestParam String username) {
        return CustomerOrderMapper.INSTANCE.listToDtoList(managerServiceIMPL.getAllCustomerOrders(username));
    }

    @GetMapping("/getExpertAllCustomerOrder")
    public List<CustomerOrderDto> getExpertAllCustomerOrder(@RequestParam String username) {
        return CustomerOrderMapper.INSTANCE.listToDtoList(managerServiceIMPL.getExpertAllCustomerOrder(username));
    }

}
