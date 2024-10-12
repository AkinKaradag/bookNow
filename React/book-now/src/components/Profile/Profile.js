import React, { useState } from 'react';
import { Button, Card, CardContent, CardActions, TextField, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import * as updatedData from "date-fns/locale";
import PrivateUserProfile from "./PrivateUserProfile";
import CompanyUserProfile from "./CompanyUserProfile";

const useStyles = makeStyles({
    root: {
        width: 400,
        margin: 'auto',
        marginTop: '20px',
        textAlign: 'center',
    },
    button: {
        margin: '10px',
    },
});

function Profile({ userData, companyData, userType }) {
    const classes = useStyles();
    const handlePrivateUserSave = (updateData) => {
        const privateUserData = {
            userName: updateData.name,
            email: updateData.email,
            password: updateData.password
        };
        fetch(`/users/${updateData.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify(privateUserData),
        })
            .then(response => {
                if (!response.ok) throw new Error("Error updating user data");
                console.log("Private user data saved successfully!");
            })
            .catch(error => console.error("Error:", error));
    };
    const handleCompanyUserSave = (updatedData) => {
        const companyUserData = {
            companyName: updatedData.companyName,
            companyAddress: updatedData.companyAddress,
            companyCity: updatedData.companyCity,
            companyPostalCode: updatedData.companyPostalCode,
            phoneNumber: updatedData.phoneNumber,
            description: updatedData.description,
            password: updatedData.password,
        };

        fetch(`/companies/${updatedData.companyId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify(companyUserData),
        })
            .then(response => {
                if (!response.ok) throw new Error("Error updating company data");
                console.log("Company user data saved successfully!");
            })
            .catch(error => console.error("Error:", error));
    };



        /*fetch(`/users/${userData.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify(updatedData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Fehler beim Speichern der Daten");
                }
                setIsEditing(false);
                // Optional: Falls du die Seite aktualisieren möchtest, um die Daten wieder abzurufen
                // setUserData(editedData);
            })
            .catch(error => console.error("Error updating user data:", error));
    };*/

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
