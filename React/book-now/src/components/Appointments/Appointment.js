import React, { useState, useEffect } from 'react';
import UpdateAppointment from "./UpdateAppointment";
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Die Appointment-Komponente zeigt eine Liste von Terminen für den eingeloggten Benutzer an.
 * Je nach Benutzertyp (PRIVATEUSER oder COMPANYUSER) werden die Termine entweder
 * des Benutzers oder der zugehörigen Firma geladen.
 */
function Appointment() {
    // useState-Hook, um den Zustand der Termine zu verwalten
    const [appointments, setAppointments] = useState([])

    const { get } = useApiRequest();

    // Laden von UserId, CompanyId und UserType aus dem Local Storage
    const userId = localStorage.getItem("currentUser");
    const companyId = localStorage.getItem("companyId");
    const userType = localStorage.getItem("userType");

    /**
     * fetchAppointments ruft Termine vom Server ab, basierend auf dem Benutzertyp.
     * Wenn der Benutzer ein PRIVATEUSER ist, werden nur seine Termine geladen,
     * bei COMPANYUSER werden alle Termine der Firma geladen.
     */
    const fetchAppointments = async () => {

        let endpoint = "/appointments";

        if (userType === "PRIVATEUSER") {
            endpoint += `?userId=${userId}`;
        } else if (userType === "COMPANYUSER") {
            endpoint += `?companyId=${companyId}`;
        }

        // GET-Anfrage, um die Termine zu erhalten
        try {
            const appointmentsData = await get(endpoint);
            setAppointments(appointmentsData);
        } catch (error) {
            console.log("Error fetching appointments:", error);
        }
    };

// useEffect, um fetchAppointments beim ersten Rendern der Komponente auszuführen
    useEffect(() => {
        fetchAppointments();
    }, []);

    return (
        <div>
            {appointments.map((appointment) => (
                <UpdateAppointment
                    key={appointment.appointmentId}
                    appointment={appointment}
                    refreshAppointments={fetchAppointments}
                />
            ))}
        </div>
    );

}

export default Appointment;
