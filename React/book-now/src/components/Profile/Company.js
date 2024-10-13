import React, { useEffect, useState } from 'react';
import Profile from './Profile';
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Diese Komponente lädt die Daten eines COMPANYUSER und zeigt das Profil in der Profile-Komponente
 * */

function Company() {
    const [companyData, setCompanyData] = useState(null); // Zustand zur Speicherung der Firmen-Daten
    const companyId = localStorage.getItem("companyId"); // Holt die Firmen-ID aus dem Local Storage
    const { get } = useApiRequest(); // Zugriff auf die GET-Funktion aus der zentralen API-Logik

    // useEffect-Hook wird aufgerufen, sobald die Komponente geladen wird
    useEffect(() => {
        // Nur wenn eine companyId vorhanden ist, wird ein Fetch gestartet
        if (companyId) {
            get(`companies/${companyId}`) // Anfrage an den Endpunkt mit der companyId und dem Token
                .then(data => {
                    data.companyId = parseInt(data.id, 10); // Konvertiert die ID in eine Integer-Variable
                    setCompanyData(data) // Setzt die empfangenen Daten in den Zustand
                })
                .catch(error => console.error('Error fetching Company data:', error)); // Fehlerhandling
        }
    }, [companyId]);

    // Die Komponente Profile wird nur gerendert, wenn companyData geladen ist
    return companyData ? (
        <Profile companyData={companyData} userType="COMPANYUSER" />
    ) : (
        <div>Loading Company Data...</div> // Anzeige eines Ladehinweises, solange die Daten fehlen
    );
}

export default Company;