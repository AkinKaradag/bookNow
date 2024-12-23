package bookNow.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Repräsentiert ein ServiceCompany-Model, das die Serviceleistungen eines Unternehmens speichert.
 */
@Entity
@Table(name = "service_companies")
public class ServiceCompanyModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) - nicht möglich mit SQLite
    private Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    CompanyModel company;


    // Getters und Setters
    public Long getServiceId() {
        return id;
    }

    public void setServiceId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

}

