package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.DiscountData;

@Repository
public interface DiscountDataRepository extends JpaRepository<DiscountData, Long> {
}