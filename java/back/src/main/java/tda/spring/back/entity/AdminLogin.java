package tda.spring.back.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_login")
public class AdminLogin {
    @Column(name = "admin_username", unique = true)
    private String adminUsername;


    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_at")
    private Date updateAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_login_id", nullable = false)
    private Long adminLoginId;

    @Column(name = "last_login")
    private String lastLogin;

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(Long adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    @PostUpdate
    public void postUpdate() {
        updateAt = new Date();
    }

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }
}