package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

@Data
public class AuthResponse {

    String message;
    Long id;
    Long companyId;
    UserType userType;
}
