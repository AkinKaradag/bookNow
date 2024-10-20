import { useRefreshToken } from "../Auth/RefreshToken";

const API_BASE_URL = 'http://localhost:3000/'; // Basis-URL für API-Anfragen
//http://localhost:3000/
// Haupt-Hook für API-Anfragen mit Token-Erneuerungslogik
const useApiRequest = () => {
    const { refreshToken } = useRefreshToken(); // Importiere die Funktion zum Erneuern des Tokens

    // Allgemeine Funktion für API-Anfragen, die alle HTTP-Methoden (GET, POST, PUT, DELETE) unterstützt
    const apiRequest = async (method, endpoint, data = null, retry = true) => {
        // Aktuellen Token aus dem Local Storage holen
        let token = localStorage.getItem("tokenKey");


        // Anfrage-Optionen definieren
        const options = {
            method: method, // HTTP-Methode (GET, POST, PUT, DELETE)
            headers: {
                'Content-Type': 'application/json', // Definiert den Inhaltstyp
                ...(token && { 'Authorization': token }), // Token-Header nur hinzufügen, wenn vorhanden
            },
            ...(data && { body: JSON.stringify(data) }), // Body nur bei POST und PUT hinzufügen
        };

        try {
            // Führe die API-Anfrage aus
            const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

            if (!response.ok) {
                if (response.status === 401 && retry) {
                    // Token abgelaufen, erneuere Token und wiederhole die Anfrage
                    token = await refreshToken();
                    localStorage.setItem("tokenKey", token);
                    return apiRequest(method, endpoint, data, false); // Wiederhole die Anfrage
                }
                // Andernfalls werfe einen Fehler mit der Statusmeldung
                const errorText = await response.text();
                throw new Error(`Request failed: ${response.status} ${errorText}`);
            }
            // Überprüfe den Antworttyp; falls JSON, parse ihn
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json(); // Gib die geparsten JSON-Daten zurück
            }
            return null; // Falls kein JSON, gib null zurück

        } catch (error) {
            // Fehler im Konsolenprotokoll ausgeben und Fehler weiterreichen
            console.error(`Error in ${method} request to ${endpoint}:`, error);
            throw error;
        }
    };

    // Funktionen für spezifische HTTP-Methoden, die `apiRequest` mit dem richtigen Methodentyp aufrufen
    const post = (endpoint, data) => apiRequest('POST', endpoint, data);
    const get = (endpoint) => apiRequest('GET', endpoint);
    const put = (endpoint, data) => apiRequest('PUT', endpoint, data);
    const del = (endpoint) => apiRequest('DELETE', endpoint);

    return { post, get, put, del };
};

export default useApiRequest;
