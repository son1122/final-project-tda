package tda.spring.back.controller.insurance.get;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import tda.spring.back.entity.CarInsurance;
import tda.spring.back.entity.PropertyInsurance;

@CrossOrigin
@RestController
public class Car {

    public ResponseEntity<CarInsurance> getProperty(){




        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}

