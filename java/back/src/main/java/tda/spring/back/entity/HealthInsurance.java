package tda.spring.back.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "health_insurance")
public class HealthInsurance {

    @JsonIgnore
    @OneToMany(mappedBy = "healthInsurance", orphanRemoval = true)
    private Collection<Insurance> insurances = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "coverage_cost")
    private Float coverageCost;

    @Column(name = "premium")
    private Float premium;

    @Column(name = "detail")
    private String detail;

    @Column(name = "emergency")
    private Boolean emergency;

    @Column(name = "ipd")
    private Long ipd;

    @Column(name = "opd")
    private Long opd;

    public Long getOpd() {
        return opd;
    }

    public void setOpd(Long opd) {
        this.opd = opd;
    }

    public Long getIpd() {
        return ipd;
    }

    public void setIpd(Long ipd) {
        this.ipd = ipd;
    }

    public Boolean getEmergency() {
        return emergency;
    }

    public void setEmergency(Boolean emergency) {
        this.emergency = emergency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getPremium() {
        return premium;
    }

    public void setPremium(Float premium) {
        this.premium = premium;
    }

    public Float getCoverageCost() {
        return coverageCost;
    }

    public void setCoverageCost(Float coverageCost) {
        this.coverageCost = coverageCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }
}