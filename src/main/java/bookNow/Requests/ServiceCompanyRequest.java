package bookNow.Requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ServiceCompanyRequest {

    private Long serviceId;

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long companyId;

}