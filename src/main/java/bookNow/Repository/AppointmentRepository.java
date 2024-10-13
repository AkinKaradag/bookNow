package bookNow.Repository;

import bookNow.Model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Dieses Repository-Interface bietet CRUD-Operationen und spezifische Datenbankabfragen für Appointment-Modelle.
 * JpaRepository bietet eine Vielzahl von Methoden zur Datenbankabfrage ohne zusätzlichen Code.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {

    /**
     * Findet eine Liste von Terminen basierend auf der Benutzer-ID.
     * @param userId die ID des Benutzers
     * @return Liste der AppointmentModel-Objekte
     */
    List<AppointmentModel> findByUser_Id(Long userId);

    /**
     * Findet eine Liste von Terminen basierend auf der Service-ID.
     * @param serviceId die ID des Services
     * @return Liste der AppointmentModel-Objekte
     */
    List<AppointmentModel> findByService_Id(Long serviceId);

    /**
     * Findet eine Liste von Terminen basierend auf der Unternehmens-ID.
     * @param companyId die ID des Unternehmens
     * @return Liste der AppointmentModel-Objekte
     */
    List<AppointmentModel> findByCompany_Id(Long companyId);


}