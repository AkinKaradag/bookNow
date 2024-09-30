package bookNow.Requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
public class ServiceCompanyRequest {

    private Long serviceId;

    private String name;

    private String description;

    private int price;

    private int duration;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long companyId;

}