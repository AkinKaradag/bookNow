package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private UserType userType;

}
