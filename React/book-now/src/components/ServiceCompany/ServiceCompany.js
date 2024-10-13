import React, { useState, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {Button, Dialog, DialogActions, DialogContent, DialogTitle, styled} from "@mui/material";
import {IconButtonProps} from "@mui/material/IconButton";
import {makeStyles} from "@mui/styles";
import {Link} from "react-router-dom";
import TextField from "@mui/material/TextField";
import {format} from "date-fns";
import useApiRequest from "../APIServices/ApiRequest";


/**
 * Die ServiceCompany-Komponente zeigt die Details eines Service-Unternehmens an.
 * Abhängig vom Benutzertyp kann ein Benutzer Termine buchen (für PRIVATEUSER) oder
 * die Services bearbeiten und löschen (für COMPANYUSER).
 * Das Layout enthält erweiterbare Inhalte, um zusätzliche Details anzuzeigen.
 */


// Stile für die Komponente definieren
const useStyle = makeStyles((theme) => ({
    root: {
        width: 800,
        textAlign: "left",
        margin: 20
    },
    media: {
        height: 0,
        paddingTop: '56.25%'
    },

    avatar: {
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration: "None",
        boxShadow: "None",
        color: "white"
    }
}));


interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
}
// Style für den Expand-Button definieren
const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme }) => ({
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
    variants: [
        {
            props: ({ expand }) => !expand,
            style: {
                transform: 'rotate(0deg)',
            },
        },
        {
            props: ({ expand }) => !!expand,
            style: {
                transform: 'rotate(180deg)',
            },
        },
    ],
}));

// Hauptkomponente
function ServiceCompany(props) {

    // Props-Variablen für die Komponente
    const {serviceId, title, description, price, duration, companyId, companyName, refreshServiceCompany, isCompanyService} = props;

    // Verschiedene State-Variablen für die Komponente
    const [expanded, setExpanded] = React.useState(false); // Steuerung der erweiterten Ansicht
    const classes = useStyle();
    const [liked, setLiked] = useState(false); // Gefällt-mir-Status
    const [openBookingForm, setOpenBookingForm] = useState(false); // Buchungsdialog-Status
    const [appointmentDate, setAppointmentDate] = useState(''); // Termin-Datum
    const [appointmentTime, setAppointmentTime] = useState(''); // Termin-Zeit
    const userType = localStorage.getItem("userType"); // Benutzertyp
    const [editMode, setEditMode] = useState(false); // Bearbeitungsmodus
    const { post, put, del } = useApiRequest();
    const [updatedService, setUpdatedService] = useState({ // Service-Details im Bearbeitungsmodus
        name: title,
        description: description,
        price: price,
        duration: duration
    });

    // Funktion zum Umschalten der erweiterten Ansicht
    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    // Funktion für Gefällt-mir-Button
    const handleLike = () => {
        setLiked(!liked);
    }

    // Funktion zum Öffnen des Buchungsdialoges
    const handleBookingOpen = () => {
        setOpenBookingForm(true);
    };

    // Funktion zum Schliessen des Buchungsdialoges
    const handleBookingClose = () => {
        setOpenBookingForm(false);
    };

    // Funktion für das Absenden der Buchung
    const handleBookingSubmit = () => {
        const bookingData = {
            appointmentDate: format(new Date(appointmentDate), 'dd-MM-yyyy'),
            appointmentTime: appointmentTime,
            serviceId: props.serviceId,
            userId: localStorage.getItem("currentUser"),
            companyId: companyId
        };

        // Anfrage zum Buchen eines Termins
        post('/appointments', bookingData)
            .then(() => {
                handleBookingClose(); // Dialog schliessen nach erfolgreicher Buchung
            })
            .catch((err) => {
                console.error("Error booking appointment:", err);
            });
    };

    // Funktion zur Aktualisierung der Eingabefelder bei Bearbeitung
    const handleInputChange = (i) => {
        const {name, value} = i.target;
        setUpdatedService({
            ...updatedService,
            [name]: value
        });
    };

    // Funktion zum Aktivieren des Bearbeitungsmodus
    const handleEdit = () => {
        setEditMode(true);
    };

    // Funktion zum Abbrechen der Bearbeitung und zum Zurücksetzen der Werte
    const handleCancel = () => {
        setEditMode(false);
        // Setze den Zustand zurück, falls nötig
        setUpdatedService({
            name: title,
            description: description,
            price: price,
            duration: duration
        });
    };

    // Funktion zum Speichern der bearbeiteten Service-Daten
    const handleSave = (serviceId) => {
        put(`service-companies/${serviceId}`, updatedService)
            .then(() => {
                refreshServiceCompany(); // Aktualisiert die Service-Liste nach Speichern
                })

            .catch((err) => {
                 console.error("Update failed", err);
            })
            .finally(() => {
                setEditMode(false); // Schaltet Bearbeitungsmodus aus
                refreshServiceCompany();
});
    };

    // Funktion zum Löschen des Services
    const handleDelete = () => {
        del(`service-companies/${serviceId}`)
            .then(() => {
                    refreshServiceCompany(); // Aktualisiert die Service-Liste nach Löschen

            })
            .catch((err) => console.error("Delete failed", err));
    }

    useEffect(() => {

    }, []);


    return(
        <div className="serviceContainer">
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className={classes.link} to={{pathname : 'companies/' + companyId}}>
                            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                {companyName.charAt(0).toUpperCase()}
                            </Avatar>
                        </Link>
                    }

                    title={title}
                    subheader={companyName}
                />

                <CardContent>
                    {editMode ? (
                        <div>
                            <input
                            name="name"
                            value={updatedService.name}
                            onChange={handleInputChange}
                            placeholder="Service Name"
                            />
                            <textarea
                                name="description"
                                value={updatedService.description}
                                onChange={handleInputChange}
                                placeholder="Description"
                            />
                            <input
                            name="price"
                            type="number"
                            value={updatedService.price}
                            onChange={handleInputChange}
                            placeholder="Price"
                            />
                            <input
                                name="duration"
                                type="number"
                                value={updatedService.duration}
                                onChange={handleInputChange}
                                placeholder="Duration (minutes)"
                            />
                            <Button onClick={() => handleSave(serviceId)} variant="contained" color="primary">
                                Save
                            </Button>
                            <Button onClick={handleCancel} variant="contained" color="secondary" style={{ marginLeft: '10px' }}>
                                Cancel
                            </Button>
                        </div>
                    ): (
                        <>
                            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                CHF {price}.-
                            </Typography>
                            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                {duration} Min
                            </Typography>
                        </>
                    ) }

                    <Dialog open={openBookingForm} onClose={handleBookingClose}>
                        <DialogTitle>Termin buchen</DialogTitle>
                        <DialogContent>
                            <TextField
                                autoFocus
                                margin="dense"
                                id="appointmentDate"
                                label="Datum"
                                type="date"
                                fullWidth
                                value={appointmentDate}
                                onChange={(e) => setAppointmentDate(e.target.value)}
                            />
                            <TextField
                                margin="dense"
                                id="appointmentTime"
                                label="Zeit"
                                type="time"
                                fullWidth
                                value={appointmentTime}
                                onChange={(e) => setAppointmentTime(e.target.value)}
                            />
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={handleBookingClose} color="secondary">Cancel</Button>
                            <Button onClick={handleBookingSubmit} color="primary">Submit</Button>
                        </DialogActions>
                    </Dialog>

                </CardContent>
                <CardActions disableSpacing>
                    <IconButton onClick={handleLike} disabled aria-label="add to favorites">
                        <FavoriteIcon style={liked? {color: "red"}: null}/>
                    </IconButton>

                    <ExpandMore
                        expand={expanded}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more"
                    >
                        <ExpandMoreIcon />
                    </ExpandMore>
                    {userType === "PRIVATEUSER" &&  (
                        <Button onClick={handleBookingOpen} variant="contained" color="primary" style={{ marginLeft: 'auto' }}>
                            Book Appointment
                        </Button>
                    )}
                    {isCompanyService && (
                        <CardActions disableSpacing>
                    {userType === "COMPANYUSER" &&  (
                        <>
                        <Button onClick={handleEdit} variant="contained" color="primary" style={{marginLeft: 'auto'}}>
                            Edit
                        </Button>
                        <Button onClick={handleDelete} variant="contained" color="secondary">
                            Delete
                        </Button>
                        </>
                    )}
                        </CardActions>
                            )}

                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <Typography>{description}</Typography>

                    </CardContent>
                </Collapse>
            </Card>

        </div>
    )

}

export default ServiceCompany;