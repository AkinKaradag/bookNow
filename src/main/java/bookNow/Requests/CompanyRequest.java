package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

@Data
public class CompanyRequest {
    private String companyName;
    private String companyAddress;
    private String companyCity;
    private int companyPostalCode;
    private String phoneNumber;
    private String description;
    private String password;
    private UserType userType;
}
