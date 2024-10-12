package bookNow.Requests;

import lombok.Data;

@Data
public class UserUpdate {

        private String userName;
        private String email;
        private String password;
}
