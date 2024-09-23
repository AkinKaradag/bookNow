package bookNow.Service;

import bookNow.Model.AppointmentModel;
import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.AppointmentRepository;
import bookNow.requests.AppointmentCreateRequest;
import bookNow.requests.AppointmentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private UserService userService;
    private CompanyService companyService;
    private ServiceCompanyService serviceCompanyService;

    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService, CompanyService companyService, ServiceCompanyService serviceCompanyService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.companyService = companyService;
        this.serviceCompanyService = serviceCompanyService;
    }

    public AppointmentModel createAppointment(AppointmentCreateRequest newAppointment) {
        UserModel user = userService.getUserById(newAppointment.getUserIdRequest());
        CompanyModel company = companyService.getCompanyById(newAppointment.getCompanyIdRequest());
        ServiceCompanyModel service = serviceCompanyService.getServiceById(newAppointment.getServiceIdRequest());
        if (user == null) {
            return null;

        } else {
            AppointmentModel toSave = new AppointmentModel();
            toSave.setAppointmentId(newAppointment.getAppointmentIdRequest());
            toSave.setAppointmentDate(newAppointment.getAppointmentDateRequest());
            toSave.setAppointmentTime(newAppointment.getAppointmentTimeRequest());
            toSave.setUser(user);
            toSave.setCompany(company);
            toSave.setService(service);
            return appointmentRepository.save(toSave);
        }

    }
        public List<AppointmentModel> getAllAppointments () {
            return appointmentRepository.findAll();
        }

        public AppointmentModel getAppointmentById (Long appointmentId){
            return appointmentRepository.findById(appointmentId).orElse(null);
        }

        public AppointmentModel updateAppointment (Long appointmentId, AppointmentUpdateRequest updatedAppointment){
            Optional<AppointmentModel> appointment = appointmentRepository.findById(appointmentId);
            if (appointment.isPresent()) {
                AppointmentModel toUpdate = appointment.get();
                toUpdate.setAppointmentDate(updatedAppointment.getAppointmentDateRequest());
                toUpdate.setAppointmentTime(updatedAppointment.getAppointmentTimeRequest());
                return appointmentRepository.save(toUpdate);
            } else {
                return null;
            }

        }

        public void deleteAppointment (Long appointmentId){
            appointmentRepository.deleteById(appointmentId);

        }


}


