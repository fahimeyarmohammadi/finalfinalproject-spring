package ir.maktab.finalprojectspring.service;

import ir.maktab.finalprojectspring.data.model.Customer;
import ir.maktab.finalprojectspring.data.model.Expert;
import ir.maktab.finalprojectspring.data.model.Manager;
import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.data.repository.ManagerRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {

    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    public UsersService(ExpertRepository expertRepository, CustomerRepository customerRepository, ManagerRepository managerRepository) {
        this.expertRepository = expertRepository;
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Expert> expert = expertRepository.findByUsername(username);
        Optional<Customer> customer = customerRepository.findByUsername(username);
        Optional<Manager> manager = managerRepository.findByUsername(username);
        if (expert.isPresent())
            return expert.get();
        if (customer.isPresent())
            return customer.get();
        if (manager.isPresent())
            return manager.get();
        throw new NotFoundException("this username not found");
    }
}
