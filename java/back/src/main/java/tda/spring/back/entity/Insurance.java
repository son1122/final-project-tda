package tda.spring.back.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
@Entity
@Table(name = "insurance")
public class Insurance {

    @ManyToOne
    @JoinColumn(name = "discount_data_id")
    private DiscountData discountData;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "location_data_id")
    private LocationData locationData;

    public enum Status {
        DRAFT,
        ACTIVE,
        EXPIRE,
        PROCESS;
    }
    public enum insuranceType {
        CAR,
        HEALTH,
        LIFE,
        PROPERTY;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "pdf")
    private Blob pdf;

    @Column(name = "price")
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_type", nullable = false)
    private Insurance.insuranceType insuranceType;

    @ManyToOne
    @JoinColumn(name = "customer_data_id")
    private CustomerData customerData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agent_data_id")
    private AgentData agentData;

    @ManyToOne
    @JoinColumn(name = "car_insurance_id")
    private CarInsurance carInsurance;

    @ManyToOne
    @JoinColumn(name = "life_insurance_id")
    private LifeInsurance lifeInsurance;

    @ManyToOne
    @JoinColumn(name = "property_insurance_id")
    private PropertyInsurance propertyInsurance;

    @ManyToOne
    @JoinColumn(name = "health_insurance_id")
    private HealthInsurance healthInsurance;

    @Column(name = "insurence_detail")
    private String insurenceDetail;

    @Column(name = "beneficial")
    private String beneficial;


    @Column(name = "expire_date")
    private String expireDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "goverment_id")
    private Integer govermentId;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "name")
    private String name;

    @Column(name = "prefix")
    private String prefix;

    public DiscountData getDiscountData() {
        return discountData;
    }

    public void setDiscountData(DiscountData discountData) {
        this.discountData = discountData;
    }

    public String getInsurenceDetail() {
        return insurenceDetail;
    }

    public void setInsurenceDetail(String insurenceDetail) {
        this.insurenceDetail = insurenceDetail;
    }

    public String getBeneficial() {
        return beneficial;
    }

    public void setBeneficial(String beneficial) {
        this.beneficial = beneficial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGovermentId() {
        return govermentId;
    }

    public void setGovermentId(Integer govermentId) {
        this.govermentId = govermentId;
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


    public HealthInsurance getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(HealthInsurance healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public PropertyInsurance getPropertyInsurance() {
        return propertyInsurance;
    }

    public void setPropertyInsurance(PropertyInsurance propertyInsurance) {
        this.propertyInsurance = propertyInsurance;
    }

    public LifeInsurance getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(LifeInsurance lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public CarInsurance getCarInsurance() {
        return carInsurance;
    }

    public void setCarInsurance(CarInsurance carInsurance) {
        this.carInsurance = carInsurance;
    }

    public AgentData getAgentData() {
        return agentData;
    }

    public void setAgentData(AgentData agentData) {
        this.agentData = agentData;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public Insurance.insuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Insurance.insuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Blob getPdf() {
        return pdf;
    }

    public void setPdf(Blob pdf) {
        this.pdf = pdf;
    }


}
