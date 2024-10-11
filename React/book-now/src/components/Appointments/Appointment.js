import React, { useState, useEffect } from 'react';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material';
import { IconButtonProps } from '@mui/material/IconButton';
import { makeStyles } from '@mui/styles';
import UpdateAppointment from "./UpdateAppointment";

const useStyle = makeStyles((theme) => ({
    root: {
        width: 800,
        textAlign: 'left',
        margin: 20,
    },
    appointmentList: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: '100%'
    },
    media: {
        height: 0,
        paddingTop: '56.25%',
    },
    avatar: {
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration: 'None',
        boxShadow: 'None',
        color: 'white',
    },
}));


function Appointment(props) {
    const {appointmentId, appointmentDate, appointmentTime, serviceId, companyId, companyName, userId, refreshAppointments } = props;
    const classes = useStyle();
    const [appointments, setAppointments] = useState([])


    const userType = localStorage.getItem('userType');







    const fetchAppointments = () => {
        const userId = localStorage.getItem("currentUser");
        const companyId = localStorage.getItem("companyId");
        const userType = localStorage.getItem("userType");

        let endpoint = "/appointments";

        if (userType === "PRIVATEUSER") {
            endpoint += `?userId=${userId}`;
        } else if (userType === "COMPANYUSER") {
            endpoint += `?companyId=${companyId}`;
        }

        fetch(endpoint, {
            headers: {
                "Authorization": localStorage.getItem("tokenKey"),
            },
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Netzwerkantwort war nicht ok');
                }
                return res.json();
            })
            .then((appointmentsData) => {
                return Promise.all(appointmentsData.map(async (appointment) =>{
                    //console.log("Service ID:", appointment.serviceId);
                    //console.log("Company ID:", appointment.companyId);
                    const serviceResponse = await fetch(`/service-companies/${appointment.serviceId}`, {
                        headers: {

                                    "Authorization": localStorage.getItem("tokenKey"),
                        }
                    });
                    const companyResponse = await fetch(`/companies/${appointment.companyId}`,{
                        headers: {
                            "Authorization": localStorage.getItem("tokenKey"),
                        }
                    });

                    const serviceData = await serviceResponse.json();
                    const companyData = await companyResponse.json();

                        return {
                            ...appointment,
                            serviceName: serviceData.name,
                            companyName: companyData.companyName,
                        };

                    }));

            })
            .then((updatedAppointment) => setAppointments(updatedAppointment))
            .catch((err) => {
                console.log("Error fetching services: ", err);
            });
    };


    useEffect(() => {
        fetchAppointments();
    }, []);

    return (
        <div>
            {appointments.map((appointment) => (
                <UpdateAppointment
                    key={appointment.id}
                    appointment={appointment}
                    refreshAppointments={fetchAppointments}
                />
            ))}
        </div>
    );


}

export default Appointment;
