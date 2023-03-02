package tda.spring.back.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tda.spring.back.entity.CustomerLogin;
import tda.spring.back.repository.CustomerLoginRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;


@RestController
public class MainController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerLoginRepository customerLoginRepository;


    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    public ResponseEntity<String> mail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("son_1122@hotmail.com");
        message.setSubject("Test Email");
        message.setText("This is a test email");
        mailSender.send(message);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @GetMapping(value = "/pdf")
    public void generatePdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get the HTML content from your view or other data source
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Bootstrap Example</title>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <link rel=\"stylesheet\" href=\"{{ public_path().'/pdf/bootstrap.min.css' }}\">\n" +
                "    <script src=\"{{ public_path().'/pdf/jquery.min.js' }}\"></script>\n" +
                "    <script src=\"{{ public_path().'/pdf/bootstrap.min.js' }}\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h2>Insurance Policy - Dhip</h2>\n" +
                "    <p>Firstname : ..........wongsatornss........ Lastname : ...........norrasing........</p>\n" +
                "    <p>ID Card : ...........123123......... Address : ..........140/164 monthaienthong village soi krathumlom 15..........</p>\n" +
                "    <p>Date of birth : ...........2023-02-28........... Email : .............son_1122@hotmail.com...........</p>\n" +
                "    <p>Beneficiary : .............test............ Price : ..............10700</p>\n" +
                "    <p>Buy date : ...............2023-02-28.............. Expire date : ............2024-02-28..........</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";

        // Set the content type and headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"example.pdf\"");

        // Create a new PDF document
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Parse the HTML content using iText's HTMLWorker
        HTMLWorker worker = new HTMLWorker(document);
        worker.parse(new StringReader(htmlContent));

        // Close the document and output stream
        document.close();
        writer.close();
        response.getOutputStream().close();
    }

    @GetMapping(value = "/login")
    public ResponseEntity<CustomerLogin> login()
    {
        try {
            CustomerLogin employ= customerLoginRepository.findById(1L).orElse(null);
            return new ResponseEntity<CustomerLogin>(employ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }

    };
}
