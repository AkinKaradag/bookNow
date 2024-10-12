package bookNow.Response;

import bookNow.Model.UserModel;
import lombok.Data;

@Data
public class UserResponse {

    Long userId;
    String userName;
    String email;
    String password;

    public UserResponse(UserModel entity){
        this.userId = entity.getId();
        this.userName = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }

}
