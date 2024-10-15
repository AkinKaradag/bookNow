const API_BASE_URL = '/api/'; // Basis-URL für API-Anfragen
//http://localhost:3000/

export const getWithoutAuth = (endpoint) => {

    return fetch (`${API_BASE_URL}${endpoint}`,  {
        method: "GET",
    })
        .then((response) => {
            if (!response.ok) {
                return response.text().then((text) => {
                    throw new Error(`Request failed: ${response.status} ${text}`);
                });
            }
            return response.json();
        });
};