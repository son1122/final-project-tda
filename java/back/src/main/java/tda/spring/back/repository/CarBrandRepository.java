package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CarBrand;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
}