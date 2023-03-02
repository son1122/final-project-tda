package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.AgentLogin;

@Repository
public interface AgentLoginRepository extends JpaRepository<AgentLogin, Long> {
    AgentLogin findByAgentUsername(String agentUsername);
}