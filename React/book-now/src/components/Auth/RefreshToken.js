import { useState } from "react";
import { postWithoutAuth } from "../APIServices/ApiPost";

/**
 * useRefreshToken-Hook: Dieser Hook stellt eine Funktion zur Verfügung, um den Access Token
 * des Benutzers zu erneuern, falls dieser abgelaufen ist. Der Prozess wird durch einen Refresh Token ermöglicht.
 */

export const useRefreshToken = () => {
    // State zur Verwaltung des Ladezustands während der Token-Erneuerung
    const [loading, setLoading] = useState(false);

    /**
     * Funktion zur Token-Erneuerung. Diese Funktion wird aufgerufen, um ein neues Access Token
     * zu erhalten, falls das aktuelle abgelaufen ist.
     */
    const refreshToken = async () => {
        const refreshToken = localStorage.getItem("refreshKey");
        const userType = localStorage.getItem("userType");

        // Überprüfen, ob der Refresh Token und der Benutzer-Typ vorhanden sind
        if (!refreshToken || !userType) {
            throw new Error("No refresh token or user type found");
        }

        // Fester Endpunkt für die Token-Erneuerung
        const path = "/auth/refresh";
        const requestData = {
            refreshToken,
            id: userType === "COMPANYUSER" ? localStorage.getItem("companyId") : localStorage.getItem("currentUser"),
            isCompanyUser: userType === "COMPANYUSER" // Boolean, der anzeigt, ob es sich um einen CompanyUser handelt
        };

        // Setze den Ladezustand auf 'true' während des Erneuerungsprozesses
        setLoading(true);

        try {
            // Sende die Anfrage zur Token-Erneuerung ohne Authentifizierung
            const response = await postWithoutAuth(path, requestData);
            // Speichere das neue Access Token, falls erfolgreich, im Local Storage
            if (response.accessToken) {
                localStorage.setItem("tokenKey", response.accessToken);
            }
            // Gebe das neue Access Token zurück
            return response.accessToken;
        } catch (error) {
            console.error("Token refresh failed:", error);
            throw error;
        } finally {
            // Ladezustand wird auf 'false' zurückgesetzt, nachdem die Anfrage abgeschlossen ist
            setLoading(false);
        }
    };
    // Rückgabe der Funktion zur Token-Erneuerung und des Ladezustands
    return { refreshToken, loading };
};
