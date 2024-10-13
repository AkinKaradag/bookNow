package bookNow.Repository;

import bookNow.Model.ServiceCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dieses Repository-Interface ermöglicht den Zugriff auf Service-Daten und bietet CRUD-Operationen.
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceCompanyModel, Long> {

    /**
     * Findet eine Liste von Service-Objekten basierend auf der Unternehmens-ID.
     * @param companyId die ID des Unternehmens
     * @return Liste der ServiceCompanyModel-Objekte
     */
    List<ServiceCompanyModel> findByCompany_Id(Long companyId);
}
