package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

@Data
public class UserRequest {

    String name;
    String email;
    String password;
    UserType userType;

}
