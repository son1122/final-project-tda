package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CarInsurance;

@Repository
public interface CarInsuranceRepository extends JpaRepository<CarInsurance, Long> {
}