import React, {useEffect, useState} from 'react';
import ServiceCompanyCreateForm from "./ServiceCompanyCreateForm";
import ServiceCompany from "./ServiceCompany";
import {makeStyles} from "@mui/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Die CreateService-Komponente erlaubt es einem COMPANYUSER, neue Services hinzuzufügen und bestehende Services anzuzeigen.
 * Sie zeigt ein Formular für die Erstellung von Services und eine Liste der bestehenden Services des Unternehmens an.
 * Nur COMPANYUSER haben Zugriff auf das Erstellen und Verwalten von Services.
 */


const useStyle = makeStyles((theme) => ({
    container: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    },
    servicesList: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: '100%'
    },
    root: {
        width: 800,
        textAlign: "left",
        margin: 20
    }
}));


function CreateService() {
    const classes = useStyle();
    const [services, setServices] = useState([]); // Zustand für die Liste der Services
    const [loading, setLoading] = useState(true); // Zustand zur Verwaltung des Ladezustands
    const userType = localStorage.getItem("userType"); // Benutzertyp aus dem Local Storage
    const companyId = localStorage.getItem("companyId"); // ID der eingeloggten Firma
    const { get } = useApiRequest(); // Zugriff auf die GET-Funktion aus der zentralen API-Logik

    // Funktion, um die Services der eingeloggten Firma zu laden
    const fetchServices = () => {

        get(`service-companies?companyId=${companyId}`)
                .then((result) => {
                    setServices(result); // Speichert die abgerufenen Services im Zustand
                    setLoading(false); // Beendet den Ladezustand
                })

            .catch((err) => {
                console.log("Error fetching services:", err); // Fehler bei der Abfrage
                setLoading(false); // Beendet den Ladezustand auch bei Fehlern
            });
    };

    // Hilfsfunktion zur Auffrischung der Service-Liste
    const refreshServiceCompany = () => {
        fetchServices()
    }

    // useEffect-Hook, um die Services beim ersten Rendern zu laden
    useEffect(() => {
        fetchServices();
    }, []);

    return (
        <div className={classes.container}>
            <h2>Create a New Service</h2>

            <p>User Type is: {userType}</p>
            {/* Formular zur Erstellung eines neuen Services, nur für Company-User */}
            {userType && userType.toUpperCase() === "COMPANYUSER" ? (
                <Card className={classes.root}>
                    <CardHeader title="Create a New Service" />
                    <CardContent>
                        <ServiceCompanyCreateForm refreshServiceCompany={refreshServiceCompany} />
                    </CardContent>
                </Card>
            ):(
                <p>Nur Company-User können Servies hinzufügen</p>
            )}

            <h2>Company Services</h2>

            {/* Liste der Services anzeigen, nur für Company-User */}
            {userType === "COMPANYUSER" && (
            <div className={classes.servicesList}>
                {loading ? (
                    <div>Loading services...</div>
                ) : (
                    services.length > 0 ? (
                        services.map((service) => (
                            <ServiceCompany
                                key={service.serviceId}
                                serviceId={service.serviceId}
                                title={service.name}
                                description={service.description}
                                price={service.price}
                                duration={service.duration}
                                companyId={service.companyId}
                                companyName={service.companyName}
                                refreshServiceCompany={refreshServiceCompany}
                                isCompanyService={true}
                            />
                        ))
                    ) : (
                        <div>No services available.</div>
                    )
                )}
            </div>
            )}
        </div>

    );

}

export default CreateService;