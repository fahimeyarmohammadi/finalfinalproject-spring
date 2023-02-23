package ir.maktab.finalprojectspring.config;

import ir.maktab.finalprojectspring.data.repository.CustomerRepository;
import ir.maktab.finalprojectspring.data.repository.ExpertRepository;
import ir.maktab.finalprojectspring.data.repository.ManagerRepository;
import ir.maktab.finalprojectspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomerRepository customerRepository;
    private final ExpertRepository expertRepository;
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(CustomerRepository customerRepository, ExpertRepository expertRepository, ManagerRepository managerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.expertRepository = expertRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/expert/register").permitAll()
                .requestMatchers("/expert/verify").permitAll()
                .requestMatchers("/customer/register").permitAll()
                .requestMatchers("/expert/**").hasRole("EXPERT")
                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated().and().httpBasic();

        return http.build();

    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//
//        auth
//                .userDetailsService(username -> managerRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new NotFoundException(String
//                             .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder).and()
//
//                .userDetailsService((username) -> expertRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new NotFoundException(String.format("this %s notFound!", username))))
//                .passwordEncoder(passwordEncoder).and()
//
//        .userDetailsService(username -> customerRepository
//                .findByUsername(username)
//                .orElseThrow(() -> new NotFoundException(String
//                        .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder);
//
//        System.out.println("hello");
//        System.out.println("");
//    }



}
