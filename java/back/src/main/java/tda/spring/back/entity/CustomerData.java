package tda.spring.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer_data", uniqueConstraints = {
        @UniqueConstraint(name = "uc_customerdata_id", columnNames = {"id", "register_id"})
})
public class CustomerData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "prefix", length = 10)
    private String prefix;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "register_id")
    private Long registerId;

    @OneToMany(mappedBy = "customerData", orphanRemoval = true)
    private Set<LocationData> locationDatas = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customerData", orphanRemoval = true)
    private Collection<Insurance> insurances = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "customerData", orphanRemoval = true)
    private CustomerLogin customerLogin;

    @Column(name = "goverment_id")
    private String govermentId;

    public CustomerLogin getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(CustomerLogin customerLogin) {
        this.customerLogin = customerLogin;
    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Set<LocationData> getLocationDatas() {
        return locationDatas;
    }

    public void setLocationDatas(Set<LocationData> locationDatas) {
        this.locationDatas = locationDatas;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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