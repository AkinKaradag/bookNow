package bookNow.Repository;

import bookNow.Model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {

    List<AppointmentModel> findByUserId(Long userId);

    List<AppointmentModel> findByCompanyId(Long companyId);

    List<AppointmentModel> findByServiceId(Long serviceId);
}

