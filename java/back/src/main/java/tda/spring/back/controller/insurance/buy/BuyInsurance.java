package tda.spring.back.controller.insurance.buy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import tda.spring.back.entity.*;
import tda.spring.back.repository.AgentDataRepository;
import tda.spring.back.repository.CustomerDataRepository;
import tda.spring.back.repository.CustomerLoginRepository;
import tda.spring.back.repository.InsuranceRepository;
import tda.spring.back.util.util;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
public class BuyInsurance {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private final CustomerDataRepository customerDataRepository;
    private final AgentDataRepository agentDataRepository;
    private final InsuranceRepository insuranceRepository;
    private final CustomerLoginRepository customerLoginRepository;

    public BuyInsurance(AgentDataRepository agentDataRepository,
                        CustomerDataRepository customerDataRepository,
                        InsuranceRepository insuranceRepository,
                        CustomerLoginRepository customerLoginRepository) {
        this.agentDataRepository = agentDataRepository;
        this.customerDataRepository = customerDataRepository;
        this.insuranceRepository = insuranceRepository;
        this.customerLoginRepository = customerLoginRepository;
    }
    @CrossOrigin
    @PostMapping("/insurance/getuser")
    public ResponseEntity<CustomerData> getProperty(HttpServletRequest request){
        Optional<CustomerData> customerData = Optional.ofNullable(customerDataRepository.findByRegisterId(Long.valueOf(request.getParameter("registerId"))));
                if(customerData.isPresent()){
                    return new ResponseEntity<CustomerData>(customerData.get(),HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
                }
    };

    @CrossOrigin
    @PostMapping("/insurance/buy")
    public void getProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getParameter("auth");
        String[] body = token.split("\\.");
        byte[] decodedBytes = Base64.getDecoder().decode(body[1]);
        String decodedString = new String(decodedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decodedString);
        String username = jsonNode.get("username").asText();

        if(request.getParameter("registerId").equals("0")){
                CustomerLogin customerLogin = new CustomerLogin();
                customerLogin.setCustomerPassword(util.generatePassword(15));
                customerLogin.setCustomerUsername(util.generatePassword(5));

                CustomerData customerData =new CustomerData();

                customerData.setName(request.getParameter("name"));
                customerData.setLastname(request.getParameter("lastname"));
                customerData.setPhone(request.getParameter("phone"));
                customerData.setEmail(request.getParameter("email"));
                customerData.setPrefix(request.getParameter("prefix"));
                String password = customerLogin.getCustomerPassword();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                customerLogin.setCustomerPassword(passwordEncoder.encode(password));
                customerDataRepository.save(customerData);
                customerLogin.setCustomerData(customerData);
                customerLoginRepository.save(customerLogin);
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
                                "    <p>Username: <strong>"+customerLogin.getCustomerUsername()+"</strong></p>\n" +
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
        }else {
            Optional<CustomerData> customerData = Optional.ofNullable(customerDataRepository.findByRegisterId(Long.valueOf(request.getParameter("registerId"))));
        }
            Optional<CustomerData> customerData = Optional.ofNullable(customerDataRepository.findByRegisterId(Long.valueOf(request.getParameter("registerId"))));
            Insurance insurance = new Insurance();
        insurance.setBeneficial(request.getParameter("beneficial"));
        Date dob=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));
        insurance.setDateOfBirth(dob);
        insurance.setEmail(request.getParameter("email"));
        insurance.setExpireDate(request.getParameter("endDate"));
        insurance.setGovermentId(Integer.valueOf(request.getParameter("GID")));
        insurance.setInsurenceDetail(request.getParameter("detail"));
        insurance.setLastname(request.getParameter("lastname"));
        insurance.setPrefix(request.getParameter("prefix"));
        Date start=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));
        insurance.setStartDate(start);
        insurance.setAgentData(agentDataRepository.findByAgentLogin_AgentUsername(username));
        insurance.setCustomerData(customerDataRepository.findByRegisterId(Long.valueOf(request.getParameter("registerId"))));
        insurance.setInsuranceType(Insurance.insuranceType.LIFE);
        insurance.setStatus(Insurance.Status.DRAFT);
        insurance.setAgentData(agentDataRepository.findById(0L).get());
        insurance.setName(request.getParameter("name"));


//        insurance

        insuranceRepository.save(insurance);




        // Set the content type and headers
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setTo("son_1122@hotmail.com");
        helper.setSubject("Dhiphaya Insurance User");

        String html =  "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            background: rgb(204, 204, 204);\n" +
                "        }\n" +
                "        .a4 {\n" +
                "            background: white;\n" +
                "            display: block;\n" +
                "            margin: 0 auto;\n" +
                "            margin-bottom: 0.5cm;\n" +
                "            box-shadow: 0 0 0.5cm rgba(0, 0, 0, 0.5);\n" +
                "            width: 21cm;\n" +
                "            height: 29.7cm;\n" +
                "            margin-left: 1cm;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"a4\">\n" +
                "    <div style=\"display: grid;\n" +
                "    grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;height: 100%;\">\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 2fr 1fr 6fr 2fr;align-items: center\">\n" +
                "            <p style=\"font-size: 0.02em;text-align: center\">สำนักใหญ่ตั้งอยู่เลขที่ <br/>1115 ถนน พระราม 3 แขวงช่องนนทรี <br/>เขตยานนาวา กรุงเทพ 10120 <br/>โทรศัพท์ 1736 0 2230 2200 <br/>โทรสาร 02230 2040 <br/>เลขประจำตัวผู้เสียภาษี <br/>0007539000533</p>\n" +
                "            <img style=\"height: 80px;text-align: center\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKRxpwFRKAno71QExzA6t51o8g6ftoqGISXJ89jsOaWEFmeruNh2kLhuhl59t_QoiPXa0&usqp=CAU\">\n" +
                "            <div style=\"display: grid;grid-template-rows: 1fr 1fr;text-align: center\">\n" +
                "                <h3 style=\"margin-block-end: 0.1em;\">บริษัท ทิพยประกันภัย จำกัด (มหาชน)</h3>\n" +
                "                <h4 style=\"margin-block-start: 0.7em;\">DHIPAYA INSURANCE PUBLIC COMPANY LIMITED</h4>\n" +
                "            </div>\n" +
                "            <p style=\"font-size: 0.02em;text-align: center\">HEAD OFFICE ADDRESS <br/>1115 RAMA3 ROAD, CHONGNGONSE <br> BANGKOK 10120 <br> TEL 1736, 0 2233 2200<br>FAX 0 2233 2240<br>www.dhipaya.co.th</p>\n" +
                "        </div>\n" +
                "        <div style=\"text-align: center\">\n" +
                "            <h3>ตารางกรรม์ประกันภัย (ขายผ่านช่องทางอิเล็กทรกนิกส์ (Online))<br>THE SCHEDULE <br>กรรม์ประกันภัยส่วนบุคคล </h3>\n" +
                "        </div>\n" +
                "        <div style=\"display: grid; grid-template-columns: 1fr 1fr\">\n" +
                "            <p style=\"margin-left: 20px;\">รหัสบริษัท : DHP<br>Company Code : DHP</p>\n" +
                "            <p>กรมธรรม์ประกันภัยเลขที่ : 999999999 <br>Policy No. : 99999999999</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr\">\n" +
                "            <p>ผู้เอาประกันภัย : ชื่อและที่อยู่ : <br>The Insured : Name and Address :</p>\n" +
                "            <p>เลขประจำตัวประชาชน : ID No.     <br> อาชีพ : Occupation : <br>อายุ : Age <br>เบอร์โทรศัพท์ : Telephone No. :</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr\">\n" +
                "            <p>ผู้รับผลประโยชน์ : ชื่อและที่อยู่ : <br>The Beneficiary : Name and Address :</p>\n" +
                "            <p>ความสัมพันธ์กับผู้เอาประกันภัย : มารดา <br>Relationship to the Insured : Mother</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr\">\n" +
                "            <p style=\"font-size: 10px\">ระยะเวลาประกันภัย <br>Period of Insurance </p>\n" +
                "            <p style=\"font-size: 10px\">: เริ่มต้นวันที่<br>: From</p>\n" +
                "            <p style=\"font-size: 10px\">1</p>\n" +
                "            <p style=\"font-size: 10px\">เวลา<br>at</p>\n" +
                "            <p style=\"font-size: 10px\">1</p>\n" +
                "            <p style=\"font-size: 10px\">น.<br>hours</p>\n" +
                "            <p style=\"font-size: 10px\">สิ้นสุดวันที่<br>To</p>\n" +
                "            <p style=\"font-size: 10px\">1</p>\n" +
                "            <p style=\"font-size: 10px\">น.<br>hours</p>\n" +
                "\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;\">\n" +
                "            <p>ข้อตกลงความคุ้มครอง(Insuring Agreement/Endosement)</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr\">\n" +
                "            <p style=\"text-align: center\">ข้อตกลงความคุ้มครอง<br>(Insuring Agreement/Endosement)</p>\n" +
                "            <p style=\"text-align: center\">จำนวนเงินเอาประกันภัย<br>Sum Insured (Bath)</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr\">\n" +
                "            <p>Description</p>\n" +
                "            <p>Money in Bath</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr 1fr 1fr\">\n" +
                "            <p>เบี้ยประกันภัยสุทธิ :    บาท<br>Net Premium :     Bath</p>\n" +
                "            <p>อากรแสตมป์ :  บาท<br>Stamps Duty:   Bath</p>\n" +
                "            <p>ภาษีมูลค่าเพิ่ม : บาท <br> VAT Bath</p>\n" +
                "            <p>เบี้ยประกันภับรวม    บาท<br>Total Premium  Bath</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr 1fr 1fr\">\n" +
                "            <p style=\"font-size: 0.4em\"><input type=\"checkbox\" class=\"onoffswitch-checkbox\" checked=\"true\"> ประกันภัยโดยตรง Direct</p>\n" +
                "            <p style=\"font-size: 0.4em\"><input type=\"checkbox\" class=\"onoffswitch-checkbox\" checked=\"true\"> ตัวแทนประกันวินาศภัย Agent</p>\n" +
                "            <p style=\"font-size: 0.4em\"><input type=\"checkbox\" class=\"onoffswitch-checkbox\" checked=\"true\"> นายหน้าประกันวินาศภัยรายนี้ Broker</p>\n" +
                "            <p style=\"font-size: 0.4em\">ใบอนุญาติเลขที่ : <br>License No. : </p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;\">\n" +
                "            <div style=\"display: grid;grid-template-columns: 1fr 1fr\">\n" +
                "                <p>วันทีทำสัญญาประกันภัย : <br>Agreement made on : </p>\n" +
                "                <p>วันออกกรมธรรม์ประกันภัย : <br>Policy issued on : </p>\n" +
                "            </div>\n" +
                "            <p style=\"font-size: 0.4em\">เพื่อเป็นหลักฐาน บริษัท โดยผู้มีอำนาจกระทำการแทนบริษัท ได้ลงลายมือชื่อและตราประทัยของบรอษัทไว้เป็นสำคัญ ณ สำนักงานของบริษัท<br>\n" +
                "                As evidence the Company has caused this policy to be signed by duty authorized persons and the Company's stamp to be affored at its office\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-left: 20px;display: grid; grid-template-columns: 1fr 1fr 1fr 1fr\">\n" +
                "         <img style=\"height: 80px;text-align: center\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKRxpwFRKAno71QExzA6t51o8g6ftoqGISXJ89jsOaWEFmeruNh2kLhuhl59t_QoiPXa0&usqp=CAU\">\n" +
                "        <img style=\"height: 80px\" src=\"https://imgur.com/qxJoHDt.png\">\n" +
                "            <img style=\"height: 80px;text-align: center\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKRxpwFRKAno71QExzA6t51o8g6ftoqGISXJ89jsOaWEFmeruNh2kLhuhl59t_QoiPXa0&usqp=CAU\">\n" +
                "            <img style=\"height: 80px\" src=\"https://imgur.com/Ncw662P.png\">\n" +
                "            <img style=\"height: 80px\" src=\"https://imgur.com/sCrM2FJ.png\">" +
                "\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";


                helper.setText(html, true);
        mailSender.send(message);





//        return new ResponseEntity<>(insurance, HttpStatus.OK);
    }


}