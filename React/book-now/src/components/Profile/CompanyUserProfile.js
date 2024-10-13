import React, {useEffect, useState} from 'react';
import { Button, Card, CardContent, CardActions, TextField, Typography } from '@mui/material';
import {makeStyles} from "@mui/styles";

/**
 * Diese Komponente zeigt die Profildaten eines COMPANYUSER und ermöglicht deren Bearbeitung.
 * */

const useStyles = makeStyles({
    root: {
        margin: 'auto',
        marginTop: '20px',
        textAlign: 'center',
    },
    button: {
        margin: '10px',
    },
});

function CompanyUserProfile({ companyData, onSave }) {
    const classes = useStyles();
    // Initialisiert den Zustand für die bearbeiteten Firmendaten
    const [editedData, setEditedData] = useState({
        companyId: companyData.companyId,
        companyName: companyData.companyName,
        companyAddress: companyData.companyAddress,
        companyCity: companyData.companyCity,
        companyPostalCode: companyData.companyPostalCode,
        phoneNumber: companyData.phoneNumber,
        description: companyData.description,
        password: ""
    });

    // Aktualisiert die Firmendaten im Zustand, falls sich die übergebenen Daten ändern
    useEffect(() => {
        if (companyData) {
            setEditedData({
                companyId: companyData.companyId,
                companyName: companyData.companyName,
                companyAddress: companyData.companyAddress,
                companyCity: companyData.companyCity,
                companyPostalCode: companyData.companyPostalCode,
                phoneNumber: companyData.phoneNumber,
                description: companyData.description,
                password: ""
            });
        }
    }, [companyData]);

    const [isEditing, setIsEditing] = useState(false); // Steuert den Bearbeitungsmodus

    // Aktualisiert die bearbeiteten Daten, wenn der Benutzer Eingaben macht
    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({ ...editedData, [name]: value });
    };

    // Speichert die Änderungen und schliesst den Bearbeitungsmodus
    const handleSaveClick = () => {
        onSave(editedData);
        setIsEditing(false);
    };

    // Zeigt einen Ladehinweis an, solange die Firmendaten nicht geladen sind
    if (!companyData) return <div>Loading...</div>; // Anzeige eines Ladehinweises, solange die Daten fehlen

    return (
        <Card className={classes.root}>
            <CardContent>
                <Typography variant="h5">Company User Profile</Typography>
                {/* Eingabefelder für die Firmendaten, bearbeitbar nur im Bearbeitungsmodus */}
                <TextField
                    label="Company Name"
                    name="companyName"
                    value={editedData.companyName}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing}
                />
                <TextField
                    label="Address"
                    name="companyAddress"
                    value={editedData.companyAddress}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
                <TextField
                    label="City"
                    name="companyCity"
                    value={editedData.companyCity}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
                <TextField
                    label="Postal Code"
                    name="companyPostalCode"
                    value={editedData.companyPostalCode}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
                <TextField
                    label="Phone Number"
                    name="phoneNumber"
                    value={editedData.phoneNumber}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
                <TextField
                    label="Description"
                    name="description"
                    value={editedData.description}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
                <TextField
                    label="Password"
                    name="password"
                    type="password"
                    value={editedData.password}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing} />
            </CardContent>
            <CardActions>
                {/* Schaltflächen zum Speichern oder Abbrechen der Änderungen */}
                {isEditing ? (
                    <>
                        <Button onClick={handleSaveClick} variant="contained" color="primary" style={{marginLeft: 'auto'}}>Save</Button>
                        <Button onClick={() => setIsEditing(false)} variant="contained" color="secondary">Cancel</Button>
                    </>
                ) : (
                    <Button onClick={() => setIsEditing(true)}>Edit</Button>
                )}
            </CardActions>
        </Card>
    );
}

export default CompanyUserProfile;



