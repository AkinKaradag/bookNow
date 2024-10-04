package bookNow.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "companies")
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String companyAddress;

    private String companyCity;

    private int companyPostalCode;

    private int phoneNumber;

    private String description;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType; // Enum für Endnutzer und Firmenkonto

    // Getters und Setters

    public Long getCompanyId() {
        return id;
    }

    public void setCompanyId(Long companyId) {
        this.id = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public int getCompanyPostalCode() {
        return companyPostalCode;
    }

    public void setCompanyPostalCode(int companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}