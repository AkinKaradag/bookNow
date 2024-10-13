import React from 'react';
import PrivateUserProfile from "./PrivateUserProfile";
import CompanyUserProfile from "./CompanyUserProfile";
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Die Profile-Komponente zeigt entweder das Profil eines PRIVATEUSER oder COMPANYUSER an und aktualisiert die Daten bei Bedarf
 * */
function Profile({ userData, companyData, userType }) {
    const { put } = useApiRequest(); // Zugriff auf die PUT-Methode aus der zentralen API-Logik
    // Funktion zum Speichern der privaten Benutzerdaten
    const handlePrivateUserSave = (updateData) => {
        const privateUserData = {
            userName: updateData.name,
            email: updateData.email,
            password: updateData.password
        };

        // Senden der aktualisierten Daten über eine PUT-Anfrage
        put(`users/${updateData.id}`, privateUserData)
            .then(() => {
                console.log("Private user data saved successfully!"); // Erfolgsbestätigung
            })
            .catch(error => console.error("Error:", error)); // Fehlerbehandlung
    };

    // Funktion zum Speichern der Firmendaten für einen CompanyUser
    const handleCompanyUserSave = (updatedData) => {
        const companyUserData = {
            userType: "COMPANYUSER",
            companyName: updatedData.companyName,
            companyAddress: updatedData.companyAddress,
            companyCity: updatedData.companyCity,
            companyPostalCode: updatedData.companyPostalCode,
            phoneNumber: updatedData.phoneNumber,
            description: updatedData.description,
            password: updatedData.password,
        };

        // Senden der aktualisierten Daten über eine PUT-Anfrage
        put(`companies/${updatedData.companyId}`, companyUserData)
            .then(() => {
                console.log("Company user data saved successfully!"); // Erfolgsbestätigung
            })
            .catch(error => console.error("Error:", error)); // Fehlerbehandlung
    };

    // Rendert entweder das PrivateUserProfile oder das CompanyUserProfile je nach Benutzerrolle
    return (
        <>
            {userType === 'PRIVATEUSER' ? (
                <PrivateUserProfile userData={userData} onSave={handlePrivateUserSave} />
            ) : (
                <CompanyUserProfile companyData={companyData} onSave={handleCompanyUserSave} />
            )}
        </>
    );
}

export default Profile;
