package tda.spring.back.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "car_brand")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_brand")
    private String carBrand;

    @Column(name = "car_country")
    private String carCountry;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "car_year_id")
    private CarYear carYear;

    @JsonIgnore
    @OneToMany(mappedBy = "carBrand", orphanRemoval = true)
    private Set<CarModel> carModels = new LinkedHashSet<>();


    public Set<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(Set<CarModel> carModels) {
        this.carModels = carModels;
    }

    public CarYear getCarYear() {
        return carYear;
    }

    public void setCarYear(CarYear carYear) {
        this.carYear = carYear;
    }

    public String getCarCountry() {
        return carCountry;
    }

    public void setCarCountry(String carCountry) {
        this.carCountry = carCountry;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}