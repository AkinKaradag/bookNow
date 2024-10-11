package bookNow.Repository;

import bookNow.Model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {

    List<AppointmentModel> findByUser_Id(Long userId);

    List<AppointmentModel> findByService_Id(Long serviceId);


    List<AppointmentModel> findByCompany_Id(Long companyId);


}