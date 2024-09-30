package bookNow.Response;

import bookNow.Model.ServiceCompanyModel;
import lombok.Data;

@Data
public class ServiceCompanyResponse {

    Long id;
    Long companyId;
    String companyName;
    String name;
    String description;
    int price;
    int duration;

    public ServiceCompanyResponse(ServiceCompanyModel entity){
        this.id = entity.getServiceId();
        this.companyId = entity.getCompany().getCompanyId();
        this.companyName = entity.getCompany().getCompanyName();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.duration = entity.getDuration();
    }

}
