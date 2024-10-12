import React, {useEffect, useState} from 'react';
import { Button, Card, CardContent, CardActions, TextField, Typography } from '@mui/material';

function PrivateUserProfile({ userData, onSave }) {
    const [editedData, setEditedData] = useState({
        id: userData.id,
        name: userData.name,
        email: userData.email,
        password: userData.password
    });

    useEffect(() => {
        if (userData) {
            setEditedData({
                id: userData.id,
                name: userData.name,
                email: userData.email,
                password: userData.password
            });
        }
    }, [userData]);

    const [isEditing, setIsEditing] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({ ...editedData, [name]: value });
    };

    const handleSaveClick = () => {
        onSave(editedData);
        setIsEditing(false);
    };

    if (!userData) return <div>Loading...</div>; // Anzeige eines Ladehinweises, solange die Daten fehlen

    return (
        <Card>
            <CardContent>
                <Typography variant="h5">Private User Profile</Typography>
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

