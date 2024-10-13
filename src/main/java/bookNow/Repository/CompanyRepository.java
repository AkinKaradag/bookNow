package bookNow.Repository;

import bookNow.Model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dieses Repository-Interface ermöglicht den Zugriff auf Unternehmensdaten und bietet CRUD-Operationen.
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {

    /**
     * Findet ein Unternehmen basierend auf dem Unternehmensnamen.
     * @param companyName der Name des Unternehmens
     * @return Das CompanyModel-Objekt des Unternehmens
     */
    CompanyModel findByCompanyName(String companyName);
}
