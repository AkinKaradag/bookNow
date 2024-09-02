package bookNow.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "appointments")
public class AppointmentModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    CompanyModel company;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    ServiceCompanyModel service;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserModel user;

    // Getters und Setters

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }



}
