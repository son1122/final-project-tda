package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CarYear;

@Repository
public interface CarYearRepository extends JpaRepository<CarYear, Long> {
}