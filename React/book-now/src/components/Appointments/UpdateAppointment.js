
import React, {useEffect, useState} from 'react'
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import {Link} from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import CardContent from "@mui/material/CardContent";
import {Button} from "@mui/material";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import IconButton from "@mui/material/IconButton";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Collapse from "@mui/material/Collapse";
import {red} from "@mui/material/colors";
import {makeStyles} from "@mui/styles";
import useApiRequest from "../APIServices/ApiRequest";

const useStyle = makeStyles((theme) => ({
    root: {
        width: 800,
        textAlign: 'left',
        margin: 20,
    },
    appointmentList: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: '100%'
    },
    media: {
        height: 0,
        paddingTop: '56.25%',
    },
    avatar: {
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration: 'None',
        boxShadow: 'None',
        color: 'white',
    },
}));

/**
 * UpdateAppointment-Komponente zeigt und bearbeitet Termine und ermöglicht die Anzeige von Detailinformationen.
 *
 * @param {Object} props - Die Komponenteneigenschaften, bestehend aus aptData (Termin-Daten) und refreshAppointments (Funktion zum Aktualisieren).
 */
function UpdateAppointment({ appointment: aptData, refreshAppointments }) {
    const classes = useStyle();
    const [expanded, setExpanded] = useState(false);
    const [editMode, setEditMode] = useState(false);
    const appointmentId = aptData.appointmentId;
    const [updatedAppointment, setUpdatedAppointment] = useState({
        appointmentDate: aptData.appointmentDate,
        appointmentTime: aptData.appointmentTime,
    });

    // ApiRequest Hook importieren und Methoden extrahieren
    const { get, put, del } = useApiRequest();

    /**
     * useEffect lädt die aktuellen Termindaten, falls eine appointmentId vorhanden ist,
     * und formatiert das Datum entsprechend.
     */
    useEffect(() => {
        if (aptData.appointmentId) {

            get(`appointments/${aptData.appointmentId}`)
                .then((data) => {

                    // Formatieren des Datums
                    const formattedDate = data.appointmentDate.replace(/-/g, '.'); // Format in dd.MM.yyyy

                    setUpdatedAppointment({
                        appointmentDate: formattedDate,
                        appointmentTime: data.appointmentTime,
                    });
                })
                .catch((err) => console.error('Error fetching appointment:', err));
        }
    }, [aptData.appointmentId]);

    /**
     * Umschalten der Sichtbarkeit der Detailinformationen.
     */
    const handleExpandClick = () => {
        setExpanded(prevState => !prevState);
    };

    /**
     * Aktiviert den Bearbeitungsmodus.
     */
    const handleEdit = () => {
        setEditMode(prevState => !prevState);
    };

    /**
     * Handhabt Änderungen an den Eingabefeldern und aktualisiert den Zustand.
     */
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUpdatedAppointment(prev => ({
            ...prev,
            [name]: value
        }));
    };


    /**
     * Speichert die bearbeiteten Termindaten und sendet sie an das Backend.
     */
    const handleSave = () => {
        // Formatieren der Daten für das Backend
        const formattedDate = updatedAppointment.appointmentDate.split('-').reverse().join('-');  // dd-MM-yyyy
        const formattedTime = updatedAppointment.appointmentTime;  // HH:mm bleibt unverändert

        const requestBody = {
            appointmentDateRequest: formattedDate,
            appointmentTimeRequest: formattedTime,
        };

        put(`appointments/${appointmentId}`, requestBody)
            .then(() => {
                setEditMode(false); // Beenden des Bearbeitungsmodus
                refreshAppointments(); // Aktualisieren der Terminliste
            })
            .catch((err) => console.log('Error updating appointment:', err));
    };

    /**
     * Löscht den Termin und aktualisiert die Terminliste.
     */
    const handleDelete = () => {
        del(`appointments/${appointmentId}`)
            .then(() => {

                    refreshAppointments();
            })
            .catch((err) => console.error('Delete failed', err));
    };

    return (
        <Card className={classes.root}>
            <CardHeader
                avatar={
                    <Link className={classes.link} to={{ pathname: 'companies/' + aptData.companyId }}>
                        <Avatar sx={{ bgcolor: red[500] }}>
                            {aptData.companyName.charAt(0).toUpperCase()}
                        </Avatar>
                    </Link>
                }
                title={`${aptData.serviceName}`}
                subheader={`${aptData.companyName}`}
            />
            <CardContent>
                {editMode ? (
                    <div>
                        <input
                            type="date"
                            name="appointmentDate"
                            value={updatedAppointment.appointmentDate}
                            onChange={handleInputChange}
                        />
                        <input
                            type="time"
                            name="appointmentTime"
                            value={updatedAppointment.appointmentTime}
                            onChange={handleInputChange}
                        />
                        <Button onClick={() => handleSave(appointmentId)} variant="contained" color="primary">Save</Button>
                    </div>
                ) : (
                    <Typography variant="body2" sx={{color: 'text.secondary'}}>
                        Date: {updatedAppointment.appointmentDate} - Time: {updatedAppointment.appointmentTime}
                    </Typography>
                )}
            </CardContent>
            <CardActions disableSpacing>
                <IconButton onClick={handleExpandClick}>
                    <ExpandMoreIcon />
                </IconButton>
                <Button onClick={handleEdit} variant="contained" color="primary" style={{ marginLeft: 'auto' }}>Edit</Button>
                <Button onClick={handleDelete} variant="contained" color="secondary" >Delete</Button>
            </CardActions>
            <Collapse in={expanded}>
                <CardContent>
                    <Typography>Company: {aptData.companyName}</Typography>
                    <Typography>Date: {updatedAppointment.appointmentDate}</Typography>
                    <Typography>Time: {updatedAppointment.appointmentTime}</Typography>
                </CardContent>
            </Collapse>
        </Card>
    );
}

export default UpdateAppointment;
