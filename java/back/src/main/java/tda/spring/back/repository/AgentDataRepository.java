package tda.spring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tda.spring.back.entity.AgentData;

@Repository
public interface AgentDataRepository extends JpaRepository<AgentData, Long> {
    AgentData findByAgentLogin_AgentUsername(String agentUsername);
    long countByNameAndLastname(String name, String lastname);
    AgentData findByName(String name);
}