package bookNow.Response;

import bookNow.Model.CompanyModel;
import lombok.Data;

@Data
public class CompanyResponse {

    Long companyId;
    String companyName;
    String companyDescription;
    String companyAddress;
    String companyCity;
    int companyPLZ;
    String companyPhone;
    String password;

    public CompanyResponse(CompanyModel entity){
        this.companyId = entity.getid();
        this.companyName = entity.getCompanyName();
        this.companyDescription = entity.getDescription();
        this.companyAddress = entity.getCompanyAddress();
        this.companyCity = entity.getCompanyCity();
        this.companyPLZ = entity.getCompanyPostalCode();
        this.companyPhone = entity.getPhoneNumber();
        this.password = entity.getPassword();
    }
}
