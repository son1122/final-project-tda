package tda.spring.back.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agent_login")
public class AgentLogin {
    @Column(name = "agent_username", nullable = false, unique = true)
    private String agentUsername;

    @Column(name = "agent_password")
    private String agentPassword;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_at")
    private Date updateAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agent_login_id", nullable = false)
    private Long agentLoginId;

    @JsonIgnore
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "agent_data_id", nullable = false)
    private AgentData agentData;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_login")
    private Date lastLogin;

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public AgentData getAgentData() {
        return agentData;
    }

    public void setAgentData(AgentData agentData) {
        this.agentData = agentData;
    }

    public Long getAgentLoginId() {
        return agentLoginId;
    }

    public void setAgentLoginId(Long agentLoginId) {
        this.agentLoginId = agentLoginId;
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
    @PostUpdate
    public void postUpdate() {
        updateAt = new Date();
    }

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}