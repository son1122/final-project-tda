package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CarModel;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}