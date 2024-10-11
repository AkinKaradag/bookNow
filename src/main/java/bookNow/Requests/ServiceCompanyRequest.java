package bookNow.Requests;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ServiceCompanyRequest {

    private Long serviceId;

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;

    private Long companyId;

}