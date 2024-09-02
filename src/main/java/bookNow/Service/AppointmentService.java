package bookNow.Service;

import bookNow.Model.AppointmentModel;
import bookNow.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentModel createAppointment(AppointmentModel appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<AppointmentModel> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public AppointmentModel getAppointmentById(Long appointmentId) {
            return appointmentRepository.findById(appointmentId).orElse(null);
    }

    public AppointmentModel updateAppointment(Long appointmentId, AppointmentModel updatedAppointment) {
        Optional<AppointmentModel> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            AppointmentModel foundAppointment = appointment.get();
            foundAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
            foundAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
            return appointmentRepository.save(updatedAppointment);
        } else {
            return null;
        }

    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);

    }

}
