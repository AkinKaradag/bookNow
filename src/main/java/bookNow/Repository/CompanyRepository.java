package bookNow.Repository;

import bookNow.Model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    CompanyModel findByName(String companyName);
}
