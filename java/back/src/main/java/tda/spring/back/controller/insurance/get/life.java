package tda.spring.back.controller.insurance.get;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tda.spring.back.entity.LifeInsurance;
import tda.spring.back.entity.PropertyInsurance;
import tda.spring.back.repository.LifeInsuranceRepository;

import java.util.List;

@CrossOrigin
@RestController
public class life {
    private final LifeInsuranceRepository lifeInsuranceRepository;

    public life(LifeInsuranceRepository lifeInsuranceRepository) {
        this.lifeInsuranceRepository = lifeInsuranceRepository;
    }


    @CrossOrigin
    @GetMapping("/insurance/life")

    public ResponseEntity<List<LifeInsurance>> getProperty(){

        List<LifeInsurance> lifeInsurance=lifeInsuranceRepository.findAll();
        return new ResponseEntity<>(lifeInsurance, HttpStatus.OK);
    }


}
