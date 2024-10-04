package bookNow.Security;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private UserType userType;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(Long id, String username, String password, UserType userType, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.authorities = authorities;
    }

    public static JwtUserDetails build(UserModel user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("user"));
                return new JwtUserDetails(
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        user.getUserType(),
                        authorities
                );
    }

    public static JwtUserDetails build(CompanyModel company) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("company"));
        return new JwtUserDetails(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getPassword(),
                UserType.COMPANYUSER,
                authorities
        );
    }


}
