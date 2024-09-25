package bookNow.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ServiceCompanyRequest {

    private Long serviceId;

    private String name;

    private String description;

    private int price;

    private int duration;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long companyId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long id) {
        this.serviceId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}