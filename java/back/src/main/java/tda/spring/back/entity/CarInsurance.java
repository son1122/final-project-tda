package tda.spring.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "car_insurance")
public class CarInsurance {
    @JsonIgnore
    @OneToMany(mappedBy = "carInsurance", orphanRemoval = true)
    private Collection<Insurance> insurances = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "plate_province")
    private String plateProvince;

    @Column(name = "plate_prefix")
    private String platePrefix;

    @Column(name = "premium")
    private Float premium;

    public Float getPremium() {
        return premium;
    }

    public void setPremium(Float premium) {
        this.premium = premium;
    }

    public String getPlatePrefix() {
        return platePrefix;
    }

    public void setPlatePrefix(String platePrefix) {
        this.platePrefix = platePrefix;
    }

    public String getPlateProvince() {
        return plateProvince;
    }

    public void setPlateProvince(String plateProvince) {
        this.plateProvince = plateProvince;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
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