package bookNow.Requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompanyUpdate {

    private String companyName;
    private String companyAddress;
    private String companyCity;
    private int companyPostalCode;
    private String phoneNumber;
    private String description;
    private String password;


}
