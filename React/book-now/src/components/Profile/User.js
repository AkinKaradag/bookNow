import React, { useEffect, useState } from 'react';
import Profile from './Profile';
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Diese Komponente lädt die Daten eines PRIVATEUSER und zeigt das Profil in der Profile-Komponente
 * */

function User() {
    const [userData, setUserData] = useState(null); // Zustand für Benutzerdaten
    const id = localStorage.getItem("currentUser"); // Aktuelle Benutzer-ID aus localStorage
    const { get } = useApiRequest(); // Zugriff auf die GET-Funktion aus der zentralen API-Logik

    useEffect(() => {
        // Startet den Abruf der Benutzerdaten nur, wenn die Benutzer-ID vorhanden ist
        if (id) {
            get(`users/${id}`)
                .then(data => {
                    setUserData(data) // Setzt die abgerufenen Benutzerdaten
                })
                .catch(error => console.error('Error fetching Company data:', error));
        }
    }, [id]); // Abhängigkeiten des Effekts

    // Zeigt die Profile-Komponente an, wenn die Benutzerdaten geladen sind, sonst eine Ladeanzeige
    return userData ? (
        <Profile userData={userData} userType="PRIVATEUSER"/>
    ) : (
        <div>Loading User Data...</div>
    );
}
export default User;
