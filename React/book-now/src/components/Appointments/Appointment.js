import React, { useState, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { Button, styled } from '@mui/material';
import { IconButtonProps } from '@mui/material/IconButton';
import { makeStyles } from '@mui/styles';
import { Link } from 'react-router-dom';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import {red} from "@mui/material/colors";

const useStyle = makeStyles((theme) => ({
    root: {
        width: 800,
        textAlign: 'left',
        margin: 20,
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

function Appointment(props) {
    const { id, appointmentDate, appointmentTime, companyId, companyName, userId, refreshAppointments } = props;
    const [expanded, setExpanded] = useState(false);
    const classes = useStyle();
    const [editMode, setEditMode] = useState(false);
    const [updatedAppointment, setUpdatedAppointment] = useState({
        appointmentDate,
        appointmentTime,
    });

    const userType = localStorage.getItem('userType');
    const currentUserId = localStorage.getItem('currentUser');

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedAppointment({ ...updatedAppointment, [name]: value });
    };

    const handleEdit = () => {
        setEditMode(true);
    };

    const handleSave = () => {
        fetch(`/appointments/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: localStorage.getItem('tokenKey'),
            },
            body: JSON.stringify(updatedAppointment),
        })
            .then((res) => res.json())
            .then(() => {
                setEditMode(false);
                refreshAppointments(); // Refresh the appointments after saving changes
            })
            .catch((err) => console.log('Error updating appointment:', err));
    };

    const handleDelete = () => {
        fetch(`/appointments/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: localStorage.getItem('tokenKey'),
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
        <div className="appointmentContainer">
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className={classes.link} to={{ pathname: 'companies/' + companyId }}>
                            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                {/*companyName.charAt(0).toUpperCase()*/}
                            </Avatar>
                        </Link>
                    }
                    title={`Appointment with ${companyName}`}
                    subheader={`${appointmentDate} at ${appointmentTime}`}
                />

                <CardContent>
                    {editMode ? (
                        <>
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
                            <Button onClick={handleSave} variant="contained" color="primary">
                                Save
                            </Button>
                        </>
                    ) : (
                        <>
                            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                {appointmentDate} - {appointmentTime}
                            </Typography>
                        </>
                    )}
                </CardContent>
                <CardActions disableSpacing>
                    <ExpandMore
                        expand={expanded}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more"
                    >
                        <ExpandMoreIcon />
                    </ExpandMore>
                    {(userType === 'COMPANYUSER' || currentUserId === userId) && (
                        <>
                            <Button onClick={handleEdit} variant="contained" color="primary" style={{ marginLeft: 'auto' }}>
                                Edit
                            </Button>
                            <Button onClick={handleDelete} variant="contained" color="secondary">
                                Delete
                            </Button>
                        </>
                    )}
                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <Typography>{`Company: ${companyName}`}</Typography>
                        <Typography>{`Date: ${appointmentDate}`}</Typography>
                        <Typography>{`Time: ${appointmentTime}`}</Typography>
                    </CardContent>
                </Collapse>
            </Card>
        </div>
    );
}

export default Appointment;
