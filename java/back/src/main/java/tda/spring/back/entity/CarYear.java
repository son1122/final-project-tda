package tda.spring.back.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "car_year")
public class CarYear {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_year", unique = true)
    private String carYear;

    @JsonIgnore
    @OneToMany(mappedBy = "carYear", orphanRemoval = true)
    private Set<CarBrand> carBrands = new LinkedHashSet<>();

    public Set<CarBrand> getCarBrands() {
        return carBrands;
    }

    public void setCarBrands(Set<CarBrand> carBrands) {
        this.carBrands = carBrands;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}