package tda.spring.back.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "agent_data")
public class AgentData {
    @JsonIgnore
    @OneToMany(mappedBy = "agentData", orphanRemoval = true)
    private Collection<Insurance> insurances = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "agentData", orphanRemoval = true)
    private AgentLogin agentLogin;

    public enum Type {
        BROKER,
        REPRESENTATIVE
    }
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "register_id")
    private Long registerId;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "license_id", unique = true)
    private Long licenseId;

    @Temporal(TemporalType.DATE)
    @Column(name = "expire_date")
    private Date expireDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_at")
    private Date updateAt;

    @JsonIgnore
    @OneToMany(mappedBy = "agentData", orphanRemoval = true)
    private Set<LocationData> locationDatas = new LinkedHashSet<>();

    public AgentLogin getAgentLogin() {
        return agentLogin;
    }

    public void setAgentLogin(AgentLogin agentLogin) {
        this.agentLogin = agentLogin;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
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

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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