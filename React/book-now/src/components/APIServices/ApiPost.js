const API_BASE_URL = 'http://localhost:3000/'; // Basis-URL für API-Anfragen
//http://localhost:3000/

export const postWithoutAuth = (endpoint, data) => {

    return fetch (`${API_BASE_URL}${endpoint}`,  {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body : JSON.stringify(data),
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