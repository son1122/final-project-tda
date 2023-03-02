package tda.spring.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_login")
public class CustomerLogin {
    @Column(name = "customer_username", unique = true)
    private String customerUsername;

    @Column(name = "customer_password")
    private String customerPassword;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_at")
    private Date updateAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_login_id", nullable = false)
    private Long customerLoginId;

    @JsonIgnore
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "customer_data_id", nullable = false)
    private CustomerData customerData;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_login")
    private Date lastLogin;

    public Date getLastLogin() {
        return lastLogin;
    }

    public CustomerLogin() {
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public Long getCustomerLoginId() {
        return customerLoginId;
    }

    public void setCustomerLoginId(Long customerLoginId) {
        this.customerLoginId = customerLoginId;
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

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
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