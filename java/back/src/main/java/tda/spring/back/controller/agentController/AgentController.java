package tda.spring.back.controller.agentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tda.spring.back.entity.AgentData;
import tda.spring.back.entity.AgentDataDto;
import tda.spring.back.entity.AgentDataMapper;
import tda.spring.back.entity.AgentLogin;
import tda.spring.back.repository.AgentDataRepository;
import tda.spring.back.repository.AgentLoginRepository;
import tda.spring.back.service.TokenService;
import tda.spring.back.util.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
public class AgentController {

    @Autowired
    private JavaMailSender mailSender;
    private final AgentDataRepository agentDataRepository;
    private final AgentLoginRepository agentLoginRepository;
    private final TokenService tokenService;


    public AgentController(AgentDataRepository agentDataRepository,
                           AgentLoginRepository agentLoginRepository,
                           TokenService tokenService) {
        this.agentDataRepository = agentDataRepository;
        this.agentLoginRepository = agentLoginRepository;
        this.tokenService = tokenService;
    }
    @CrossOrigin(origins = "http://localhost:3035")
    @RequestMapping(value = "/agent/logindata", method = RequestMethod.POST)
    public ResponseEntity<AgentLogin> getLogin(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getParameter("auth");
        String[] body = token.split("\\.");
        byte[] decodedBytes = Base64.getDecoder().decode(body[1]);
        String decodedString = new String(decodedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decodedString);
        String username = jsonNode.get("username").asText();
        AgentLogin agentLogin = agentLoginRepository.findByAgentUsername(username);

        return new ResponseEntity<>(agentLogin,HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3035")
    @RequestMapping(value = "/agent/profiledata", method = RequestMethod.POST)
    public ResponseEntity<AgentData> getData(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getParameter("auth");
        String[] body = token.split("\\.");
        byte[] decodedBytes = Base64.getDecoder().decode(body[1]);
        String decodedString = new String(decodedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decodedString);
        String username = jsonNode.get("username").asText();
        AgentData agentData = agentDataRepository.findByAgentLogin_AgentUsername(username);
//        AgentDataDto res = agentDataMapper.toDto(agentData);

        return new ResponseEntity<>(agentData,HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3035")
    @RequestMapping(value = "/agent/logindata/edit", method = RequestMethod.POST)
    public ResponseEntity<String> editProfile(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getParameter("auth");
        String[] body = token.split("\\.");
        byte[] decodedBytes = Base64.getDecoder().decode(body[1]);
        String decodedString = new String(decodedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decodedString);
        String username = jsonNode.get("username").asText();

        AgentLogin agentLogin =  agentLoginRepository.findByAgentUsername(username);


        agentLogin.setAgentUsername(request.getParameter("username"));
        agentLogin.setAgentPassword(request.getParameter("password"));

        agentLogin.setUpdateAt(new Date());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        agentLogin.setAgentPassword(passwordEncoder.encode(agentLogin.getAgentPassword()));
        agentLoginRepository.save(agentLogin);

        return new ResponseEntity<>("complete",HttpStatus.ACCEPTED);
    }
    @CrossOrigin(origins = "http://localhost:3035")
    @RequestMapping(value = "/agent/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpServletRequest request){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<AgentLogin> employ = Optional.ofNullable(agentLoginRepository.findByAgentUsername(request.getParameter("username")));
        if(employ.isPresent()&&passwordEncoder.matches(request.getParameter("password"),employ.get().getAgentPassword())){
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(request.getParameter("id"),
                            request.getParameter("password"),
                            authorities);

//            authentication.setAuthenticated(true);
            authentication.setDetails(employ.get().getAgentUsername());
            String token = tokenService.generateToken(authentication);
            employ.get().setLastLogin(new Date());
            agentLoginRepository.save(employ.get());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else {
            String res = "dsf";
            return new ResponseEntity<>(request.getParameter("username"),HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3035")
    @RequestMapping(value = "/agent/register", method = RequestMethod.POST)
    public ResponseEntity<String> regis(HttpServletRequest request) throws ParseException, MessagingException {

        Long count = agentDataRepository.countByNameAndLastname(request.getParameter("name"),request.getParameter("lastName"));
        if(count==0){
            //FIN
            AgentLogin agentLogin = new AgentLogin();
            agentLogin.setAgentUsername(util.generatePassword(5));
            agentLogin.setAgentPassword(util.generatePassword(10));

            AgentData agentData = new AgentData();
            agentData.setEmail(request.getParameter("email"));
            if(request.getParameter("type").equals("BROKER")){
                agentData.setType(AgentData.Type.BROKER);
            }else {
                agentData.setType(AgentData.Type.REPRESENTATIVE);
            }
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expire"));
            agentData.setExpireDate(date1);
            agentData.setName(request.getParameter("name"));
            agentData.setLastname(request.getParameter("lastName"));
            agentData.setPhone(request.getParameter("phone"));
            agentData.setPrefix(request.getParameter("prefix"));
            double random = Math.random()*100000;
            agentData.setLicenseId(Long.valueOf(request.getParameter("license")));
            agentData.setRegisterId((long) random);

            String password = agentLogin.getAgentPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            agentLogin.setAgentPassword(passwordEncoder.encode(password));
            agentDataRepository.save(agentData);
            agentLogin.setAgentData(agentData);
            agentLoginRepository.save(agentLogin);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setTo("son_1122@hotmail.com");
            helper.setSubject("Dhiphaya Insurance User");

            String html =
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "  <meta charset=\"utf-8\">\n" +
                            "  <title>Welcome to Dhipaya Insurance!</title>\n" +
                            "  <style>\n" +
                            "    body {\n" +
                            "      font-family: Arial, sans-serif;\n" +
                            "      font-size: 16px;\n" +
                            "      line-height: 1.4;\n" +
                            "      color: #444444;\n" +
                            "    }\n" +
                            "\n" +
                            "    h1 {\n" +
                            "      font-size: 24px;\n" +
                            "      margin-bottom: 20px;\n" +
                            "    }\n" +
                            "\n" +
                            "    p {\n" +
                            "      margin-bottom: 10px;\n" +
                            "    }\n" +
                            "\n" +
                            "    strong {\n" +
                            "      font-weight: bold;\n" +
                            "    }\n" +
                            "\n" +
                            "    .container {\n" +
                            "      max-width: 600px;\n" +
                            "      margin: 0 auto;\n" +
                            "      padding: 20px;\n" +
                            "      background-color: #f5f5f5;\n" +
                            "      border: 1px solid #dddddd;\n" +
                            "      border-radius: 10px;\n" +
                            "      box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);\n" +
                            "    }\n" +
                            "\n" +
                            "    .logo {\n" +
                            "      display: block;\n" +
                            "      margin: 0 auto;\n" +
                            "      margin-bottom: 20px;\n" +
                            "      width: 100px;\n" +
                            "    }\n" +
                            "\n" +
                            "    .button {\n" +
                            "      display: inline-block;\n" +
                            "      padding: 8px 12px;\n" +
                            "      border-radius: 5px;\n" +
                            "      background-color: rgba(15, 14, 159, 1);\n" +
                            "      color: #ffffff;\n" +
                            "      font-size: 16px;\n" +
                            "      text-decoration: none;\n" +
                            "      margin-top: 20px;\n" +
                            "    }\n" +
                            "\n" +
                            "    .button:hover {\n" +
                            "      background-color: rgba(15, 14, 159, 0.8);\n" +
                            "    }\n" +
                            "\n" +
                            "    .footer {\n" +
                            "      margin-top: 20px;\n" +
                            "      text-align: center;\n" +
                            "      font-size: 12px;\n" +
                            "      color: #888888;\n" +
                            "    }\n" +
                            "  </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "  <div class=\"container\">\n" +
                            "    <img src=\"https://www.dhipaya.co.th/uploads/ir_news/125/desktop/thai/8214582536685361a3f95b29f4e61762.jpg\" alt=\"Dhipaya Insurance Logo\" class=\"logo\">\n" +
                            "    <h1>Welcome to Dhipaya Insurance!</h1>\n" +
                            "    <p>Dear valued customer,</p>\n" +
                            "    <p>We are thrilled to have you on board and excited for you to experience our top-notch insurance services.</p>\n" +
                            "    <p>To get started, please use the following credentials to log in to the app:</p>\n" +
                            "    <p>Username: <strong>"+agentLogin.getAgentUsername()+"</strong></p>\n" +
                            "    <p>Password: <strong>"+password+"</strong><br/></p>\n" +
                            "    <p>After Login Please Change Password and Username Immediately</p>"+
                            "    <p>If you have any questions or concerns, don't hesitate to reach out to our customer support team. We're here to assist you 24/7.</p>\n" +
                            "    <a href=\"#\" class=\"button\">Get Start</a>\n" +
                            "    <p>Thank you for choosing Dhipaya Insurance. We look forward\n" +
                            "We look forward to serving you.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "The Dhipaya Insurance Team\n" +
                            "          <div class=\"footer\">\n" +
                            "      This email was sent by the Dhipaya . &copy; 2023 Company Inc. All rights reserved.\n" +
                            "    </div>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</body>\n" +
                            "\n" +
                            "</html>\n";

            helper.setText(html, true);
            mailSender.send(message);


            return new ResponseEntity<>("complete", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("user exist", HttpStatus.CONFLICT);
        }
//        return new ResponseEntity<>(request.getParameter("type"),HttpStatus.OK);
    }
}
