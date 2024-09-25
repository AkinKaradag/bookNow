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
import org.springframework.web.bind.annotation.PathVariable;

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
        UserModel user = userService.getUserById(newAppointment.getUserId());
        CompanyModel company = companyService.getCompanyById(newAppointment.getCompanyId());
        ServiceCompanyModel service = serviceCompanyService.getServiceById(newAppointment.getServiceId());
        if (user == null) {
            return null;
        } else if (company == null) {
            return null;
        } else if (service == null) {
            return null;
        } else {
        AppointmentModel toSave = new AppointmentModel();
        toSave.setAppointmentId(newAppointment.getAppointmentId());
        toSave.setAppointmentDate(newAppointment.getAppointmentDate());
        toSave.setAppointmentTime(newAppointment.getAppointmentTime());
        toSave.setUser(user);
        toSave.setCompany(company);
        toSave.setService(service);
        return appointmentRepository.save(toSave);
        } }

        public List<AppointmentModel> getAllAppointments () {
            return appointmentRepository.findAll();
        }

        public AppointmentModel getAppointmentById (@PathVariable Long appointmentId){
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


