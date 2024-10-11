
import React, {useEffect, useState} from 'react'
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import {Link} from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import CardContent from "@mui/material/CardContent";
import {Button, styled} from "@mui/material";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import IconButton, {IconButtonProps} from "@mui/material/IconButton";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Collapse from "@mui/material/Collapse";
import {red} from "@mui/material/colors";
import {makeStyles} from "@mui/styles";

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

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
}

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

function UpdateAppointment({ appointment: aptData, refreshAppointments }) {
    const classes = useStyle();
    const [expanded, setExpanded] = useState(false);
    const [editMode, setEditMode] = useState(false);
    const [updatedAppointment, setUpdatedAppointment] = useState({
        appointmentDate: aptData.appointmentDate,
        appointmentTime: aptData.appointmentTime,
    });

    useEffect(() => {
        if (aptData.appointmentId) {
            fetch(`/appointments/${aptData.appointmentId}`, {
                headers: {
                    'Authorization': localStorage.getItem('tokenKey'),
                },
            })
                .then((res) => res.json())
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

    const handleExpandClick = () => {
        setExpanded(prevState => !prevState);
    };

    const handleEdit = () => {
        setEditMode(prevState => !prevState);
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUpdatedAppointment(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const appointmentId = aptData.appointmentId;

    const handleSave = () => {
        console.log("AptData:", aptData);  // Prüfen, ob aptData korrekt vorhanden ist

        console.log("Appointment ID:", appointmentId);
        if (!appointmentId) {
            console.error("Fehler: Termin-ID ist nicht definiert.");
            return;
        }

        // Formatieren der Daten für das Backend
        const formattedDate = updatedAppointment.appointmentDate.split('-').reverse().join('-');  // dd-MM-yyyy
        const formattedTime = updatedAppointment.appointmentTime;  // HH:mm bleibt unverändert

        const requestBody = {
            appointmentDateRequest: formattedDate,
            appointmentTimeRequest: formattedTime,
        };

        console.log("Update Appointment: ", requestBody, "Appointment ID: ", aptData.id);

        fetch(`/appointments/${appointmentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": localStorage.getItem('tokenKey'),
            },
            body: JSON.stringify(requestBody),
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`Error updating appointment: ${res.statusText}`);
                }
                return res.json()
            })
            .then(() => {
                setEditMode(false);
                refreshAppointments();
            })
            .catch((err) => console.log('Error updating appointment:', err));
    };

    const handleDelete = () => {
        fetch(`/appointments/${appointmentId}`, {
            method: 'DELETE',
            headers: {
                "Authorization": localStorage.getItem('tokenKey'),
            },
        })
            .then((res) => {
                if (res.ok) {
                    refreshAppointments(); // Refresh the appointments after deletion
                }
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
