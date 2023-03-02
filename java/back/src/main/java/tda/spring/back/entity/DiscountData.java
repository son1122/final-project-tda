package tda.spring.back.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "discount_data")
public class DiscountData {
    public enum Loyalty {
        START,
        MEDIUM,
        LOYAL
    }
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "marry")
    private Boolean marry;

    @Column(name = "accident")
    private Boolean accident;

    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty")
    private Loyalty loyalty;

    @JsonIgnore
    @OneToMany(mappedBy = "discountData", orphanRemoval = true)
    private Collection<Insurance> insurances = new ArrayList<>();

    @Column(name = "discount")
    private Float discount;

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty loyalty) {
        this.loyalty = loyalty;
    }

    public Boolean getAccident() {
        return accident;
    }

    public void setAccident(Boolean accident) {
        this.accident = accident;
    }

    public Boolean getMarry() {
        return marry;
    }

    public void setMarry(Boolean marry) {
        this.marry = marry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}