package bookNow.requests;


import lombok.Data;

@Data
public class ServiceCompanyUpdate {

    private String name;

    private String description;

    private int price;

    private int duration;


}
