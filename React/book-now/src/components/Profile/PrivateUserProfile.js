import React, {useEffect, useState} from 'react';
import { Button, Card, CardContent, CardActions, TextField, Typography } from '@mui/material';

/**
 * Diese Komponente zeigt die Profildaten eines PRIVATEUSER und ermöglicht deren Bearbeitung.
 * */

function PrivateUserProfile({ userData, onSave }) {
    // Initialisiert den Zustand für die bearbeiteten Nutzerdaten
    const [editedData, setEditedData] = useState({
        id: userData.id,
        name: userData.name,
        email: userData.email,
        password: ""
    });

    // Aktualisiert die bearbeiteten Daten, wenn sich die übergebenen Benutzerdaten ändern
    useEffect(() => {
        if (userData) {
            setEditedData({
                id: userData.id,
                name: userData.name,
                email: userData.email,
                password: ""
            });
        }
    }, [userData]);

    const [isEditing, setIsEditing] = useState(false); // Zustand zur Steuerung des Bearbeitungsmodus

    // Handhabung der Änderungen an den Eingabefeldern
    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({ ...editedData, [name]: value });
    };

    // Speichern der Änderungen und Beenden des Bearbeitungsmodus
    const handleSaveClick = () => {
        onSave(editedData);
        setIsEditing(false);
    };

    // Anzeige eines Ladehinweises, solange die Benutzerdaten nicht geladen sind
    if (!userData) return <div>Loading...</div>;

    return (
        <Card>
            <CardContent>
                <Typography variant="h5">Private User Profile</Typography>
                {/* Eingabefelder für Benutzerdaten, die im Bearbeitungsmodus aktiviert werden */}
                <TextField
                    label="Name"
                    name="name"
                    value={editedData.name}
                    onChange={handleChange}
                    fullWidth
                    disabled={!isEditing}
                />
                <TextField
                    label="Email"
                    name="email"
                    value={editedData.email}
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

export default PrivateUserProfile;

