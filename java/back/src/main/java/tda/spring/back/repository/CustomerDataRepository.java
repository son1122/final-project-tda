package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CustomerData;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerData, Long> {
    CustomerData findByRegisterId(Long registerId);
}