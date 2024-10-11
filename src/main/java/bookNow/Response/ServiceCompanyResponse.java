package bookNow.Response;

import bookNow.Model.ServiceCompanyModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceCompanyResponse {

    Long serviceId;
    Long companyId;
    String companyName;
    String name;
    String description;
    BigDecimal price;
    int duration;

    public ServiceCompanyResponse(ServiceCompanyModel entity){
        this.serviceId = entity.getServiceId();
        this.companyId = entity.getCompany().getid();
        this.companyName = entity.getCompany().getCompanyName();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.duration = entity.getDuration();
    }

}
