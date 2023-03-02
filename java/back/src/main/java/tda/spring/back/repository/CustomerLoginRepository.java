package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.CustomerLogin;

@Repository
public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, Long> {
}