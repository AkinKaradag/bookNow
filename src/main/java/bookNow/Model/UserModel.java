package bookNow.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

/**
 * Repräsentiert ein User-Model, das die Daten eines Nutzers speichert.
 */
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) - nicht möglich mit SQLite
    private Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

    private String name;
    private String email;
    private String password;


    @Enumerated(EnumType.STRING)
    private UserType userType; // Enum für Endnutzer und Firmenkonto

    // Getters und Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}


