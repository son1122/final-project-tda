package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.LocationData;

@Repository
public interface LocationDataRepository extends JpaRepository<LocationData, Long> {
}