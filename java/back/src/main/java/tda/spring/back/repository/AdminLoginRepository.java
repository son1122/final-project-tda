package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.AdminLogin;


@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin, Long> {
}