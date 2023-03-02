package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.LifeInsurance;

@Repository
public interface LifeInsuranceRepository extends JpaRepository<LifeInsurance, Long> {
}