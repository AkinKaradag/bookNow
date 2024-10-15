package bookNow.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

/**
 * Repräsentiert ein Refresh-Token-Model, welches für die Authentifizierung in der Datenbank gespeichert wird.
 */
@Entity
@Table(name = "refresh_token")
@Data
public class RefreshTokenModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = true)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        private UserModel user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id", nullable = true)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        private CompanyModel company;

        @Column(nullable = false, unique = true)
        private String token;

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date expiryDate;

        // Methode zur Feststellung, ob es sich um einen CompanyUser handelt
        public boolean isCompanyUser() {
            return company != null;
        }

        public void setUser(UserModel user) {
                this.user = user;
        }

        public void setCompany(CompanyModel company) {
                this.company = company;
        }

        public Date getExpiryDate() {
                return this.expiryDate;
        }

        public String getToken() {
                return this.token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public void setExpiryDate(Date expiryDate) {
                this.expiryDate = expiryDate;
        }

        public CompanyModel getCompany() {
                return this.company;
        }

        public UserModel getUser() {
                return this.user;
        }




}

