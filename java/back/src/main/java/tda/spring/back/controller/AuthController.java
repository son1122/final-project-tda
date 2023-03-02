package tda.spring.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tda.spring.back.entity.AgentData;
import tda.spring.back.entity.AgentLogin;
import tda.spring.back.repository.AgentDataRepository;
import tda.spring.back.repository.AgentLoginRepository;
import tda.spring.back.service.TokenService;


import java.util.Optional;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

//    @Autowired
//    private EmployeeRepository employeeRepository;

    @Autowired
    private AgentLoginRepository agentLoginRepository;

    @PostMapping("/token")//Authentication authentication

    public String token(@RequestParam("id") Integer id,
                        @RequestParam("password") String password) {

        try {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Optional<AgentLogin> employ = agentLoginRepository.findById(Long.valueOf(id));
            Boolean compareResult = passwordEncoder.matches(password, employ.get().getAgentPassword());
            System.out.println(compareResult);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id, password);
            authentication.setAuthenticated(true);
            System.out.println("Token requested for user: '{}' " + authentication.getName());
            String token = tokenService.generateToken(authentication);
            System.out.println("Token granted: {} " + token);
            return token;
        } catch (Exception e) {
            return "Error";
        }
    }

    @Autowired
    private AgentDataRepository agentDataRepository;
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String passwword){
        AgentData data = agentDataRepository.findByName(username);
        if(data.getName().equals(passwword)){
            return data.getId().toString();
        }
        else{
            return "Error";
        }
    }
}


