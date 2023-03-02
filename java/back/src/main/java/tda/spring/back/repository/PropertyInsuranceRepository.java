package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.PropertyInsurance;

@Repository
public interface PropertyInsuranceRepository extends JpaRepository<PropertyInsurance, Long> {
}