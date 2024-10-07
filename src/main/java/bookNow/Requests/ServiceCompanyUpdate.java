package bookNow.Requests;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceCompanyUpdate {

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;


}
