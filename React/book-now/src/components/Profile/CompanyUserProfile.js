import React, {useEffect, useState} from 'react';
import { Button, Card, CardContent, CardActions, TextField, Typography } from '@mui/material';
import {makeStyles} from "@mui/styles";

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
    const [editedData, setEditedData] = useState({
        companyId: companyData.companyId,
        companyName: companyData.companyName,
        companyAddress: companyData.companyAddress,
        companyCity: companyData.companyCity,
        companyPostalCode: companyData.companyPostalCode,
        phoneNumber: companyData.phoneNumber,
        description: companyData.description,
        password: companyData.password
    });

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
                password: companyData.password
            });
        }
    }, [companyData]);

    const [isEditing, setIsEditing] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({ ...editedData, [name]: value });
    };

    const handleSaveClick = () => {
        onSave(editedData);
        setIsEditing(false);
    };

    if (!companyData) return <div>Loading...</div>; // Anzeige eines Ladehinweises, solange die Daten fehlen

    return (
        <Card className={classes.root}>
            <CardContent>
                <Typography variant="h5">Company User Profile</Typography>
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



