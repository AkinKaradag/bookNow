import React, {useEffect, useState} from "react";
import ServiceCompany from "../ServiceCompany/ServiceCompany";
import {makeStyles} from "@mui/styles";
import {getWithoutAuth} from "../APIServices/ApiGet";

/**
 * Die Services-Komponente zeigt eine Liste aller verfügbaren Services an.
 * Sie lädt die Service-Daten ohne Authentifizierung und zeigt sie im Layout der Seite an.
 * Fehler oder Ladezustände werden entsprechend behandelt.
 */


const useStyles = makeStyles((theme) => ({
    container: {
        display:"flex",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: '#DCCFCF'
    }
}));

// Hauptkomponente für die Anzeige aller verfügbaren Services
function Services(){
    const [error, setError] = useState(null); // Zustand für Fehler, falls eintritt
    const [isLoaded, setIsLoaded] = useState(false); // Zustand für den Ladezustand
    const [serviceCompaniesList, setServiceCompaniesList] = useState([]); // Zustand für die Liste der Services
    const classes = useStyles(); // Verwenden der definierten Styles

    // Funktion zum Abrufen der Services vom Server
    const refreshServiceCompany = () => {
        getWithoutAuth(`service-companies`)
            .then(
                (result) => {
                    setIsLoaded(true); // Markieren als geladen
                    setServiceCompaniesList(result); // Setzen der empfangenen Services
                },
                (error) => {
                    console.log(error)
                    setIsLoaded(true); // Markieren als geladen, auch bei Fehler
                    setError(error); // Fehlerzustand setzen
                }
            )
    }

    // Verwenden von useEffect, um die Services nur einmal bei der Initialisierung zu laden
    useEffect(() => {
        refreshServiceCompany();
    }, []);

    // Fehlermeldung anzeigen, wenn ein Fehler aufgetreten ist
    if (error) {
        return <div > Error!!! < /div>;
    }
    // Ladeanzeige anzeigen, solange die Daten geladen werden
    else if (!isLoaded) {
        return <div > Loading... < /div>;
    }
    // Services anzeigen, sobald die Daten geladen sind und kein Fehler vorliegt
    else {
        return (

            <div className={classes.container}>

                {serviceCompaniesList.map(serviceCompany => (
                    <ServiceCompany key={serviceCompany.serviceId} title={serviceCompany.name} serviceId={serviceCompany.serviceId}
                                    companyName={serviceCompany.companyName} companyId={serviceCompany.companyId}
                                    description={serviceCompany.description} price={serviceCompany.price} duration={serviceCompany.duration}></ServiceCompany>
                ))
                } </div>

        );
    }
}

export default Services;