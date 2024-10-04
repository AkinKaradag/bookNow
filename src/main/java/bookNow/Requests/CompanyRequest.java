package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

@Data
public class CompanyRequest {
    String companyName;
    String companyAddress;
    String companyCity;
    int companyPostalCode;
    int phoneNumber;
    String description;
    String password;
    UserType userType;
}
