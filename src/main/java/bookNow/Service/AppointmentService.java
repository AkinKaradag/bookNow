package bookNow.Service;

import bookNow.Model.AppointmentModel;
import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.AppointmentRepository;
import bookNow.Requests.AppointmentCreateRequest;
import bookNow.Requests.AppointmentUpdateRequest;
import bookNow.Response.AppointmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        UserModel user = userService.findByUserId(newAppointment.getUserId());
        CompanyModel company = companyService.findById(newAppointment.getCompanyId());
        ServiceCompanyModel service = serviceCompanyService.findByServiceId(newAppointment.getServiceId());
        if (user == null && company == null && service == null) {
            throw new IllegalArgumentException("User, Company or Service not found");
        }
            AppointmentModel toSave = new AppointmentModel();
            toSave.setAppointmentId(newAppointment.getAppointmentId());
            toSave.setAppointmentDate(newAppointment.getAppointmentDate());
            toSave.setAppointmentTime(newAppointment.getAppointmentTime());
            toSave.setUser(user);
            toSave.setCompany(company);
            toSave.setService(service);
            return appointmentRepository.save(toSave);
       }

        public List<AppointmentResponse> getAllAppointments (Optional <Long> userId, Optional <Long> serviceId, Optional <Long> companyId){
            List<AppointmentModel> listAppointments;
            if (userId.isPresent()) {
                listAppointments = appointmentRepository.findByUser_Id(userId.get());
            } else if (companyId.isPresent()) {
                listAppointments = appointmentRepository.findByCompany_Id(companyId.get());
            } else if (serviceId.isPresent()) {
                listAppointments = appointmentRepository.findByService_Id(serviceId.get());
            } else {
                listAppointments = appointmentRepository.findAll();
            }
            return listAppointments.stream().map(AppointmentResponse::new).collect(Collectors.toList());
        }

        public AppointmentModel findByAppointmentId(Long appointmentId){
            return appointmentRepository.findById(appointmentId).orElse(null);
        }

        public AppointmentModel updateAppointment(Long appointmentId, AppointmentUpdateRequest updatedAppointment){
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


