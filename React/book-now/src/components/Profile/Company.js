//import Avatar from "../Avatar/Avatar";
import React, { useEffect, useState } from 'react';
import Profile from './Profile';
import CompanyUserProfile from "./CompanyUserProfile";

function Company() {
    const [companyData, setCompanyData] = useState(null);
    const companyId = localStorage.getItem("companyId");
    const token = localStorage.getItem("tokenKey");

    console.log("Loaded companyId from localStorage:", companyId);

    useEffect(() => {
        // Nur dann einen Fetch starten, wenn companyId vorhanden ist
        if (companyId) {
            fetch(`/companies/${companyId}`, {
                headers: {
                    "Authorization": token,
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Fetched data:", data);
                    data.companyId = parseInt(data.id, 10);
                    console.log("Parsed companyId:", data.companyId);
                    setCompanyData(data)
                })
                .catch(error => console.error('Error fetching Company data:', error));
        }
    }, [companyId, token]);

    // Nur rendern, wenn companyData geladen ist
    return companyData ? (
        <Profile companyData={companyData} userType="COMPANYUSER" />
    ) : (
        <div>Loading Company Data...</div>
    );
}

export default Company;