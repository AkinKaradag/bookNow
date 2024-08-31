package bookNow.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_companies")
public class ServiceCompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Getters und Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

