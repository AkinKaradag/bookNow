package bookNow.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicesCompany")
public class ServiceCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int duration; // in minutes

    // Getters and Setters
}
