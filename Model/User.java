package Model;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType; // Enum for differentiating between Endnutzer and Firmenkonto

    // Getters and Setters
}

