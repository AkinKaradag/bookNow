import React, { useState, useEffect } from 'react';
//import ReactDOM from 'react-dom';
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

function ServiceCompany(props) {

    const {id, title, description, price, duration, companyId, companyName, refreshServiceCompany} = props;
    const [expanded, setExpanded] = React.useState(false);
    const classes = useStyle();
    const [liked, setLiked] = useState(false);
    const [openBookingForm, setOpenBookingForm] = useState(false);
    const [appointmentDate, setAppointmentDate] = useState('');
    const [appointmentTime, setAppointmentTime] = useState('');
    const userType = localStorage.getItem("userType");
    const currentUser = localStorage.getItem("currentUser");
    const [editMode, setEditMode] = useState(false);
    const [updatedService, setUpdatedService] = useState({
        name: title,
        description: description,
        price: price,
        duration: duration
    });


    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleLike = () => {
        setLiked(!liked);
    }

    const handleBookingOpen = () => {
        setOpenBookingForm(true);
    };

    const handleBookingClose = () => {
        setOpenBookingForm(false);
    };

    const handleBookingSubmit = () => {
        const bookingData = {
            appointmentDate: format(new Date(appointmentDate), 'dd-MM-yyyy'),
            appointmentTime: appointmentTime,
            serviceId: props.id,
            userId: localStorage.getItem("currentUser"),
            companyId: companyId
        };

        fetch("/appointments", {
            method: "POST",
            headers: {
                "content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify(bookingData)
        })
            .then((res) => res.json())
            .then((result) => {
                console.log("Appointment booked successfully:", result);
                handleBookingClose();
            })
            .catch((error) => {
                console.error("Error booking appointment:", error);
            });
    };

    const handleInputChange = (i) => {
        const {name, value} = i.target;
        setUpdatedService({
            ...updatedService,
            [name]: value
        });
    };

    const handleEdit = () => {
        setEditMode(true);
    };

    const handleSave = () => {
        fetch(`service-companies/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify(updatedService)
        })
            .then((res) => res.json())
            .then(() => {
                setEditMode(false);
                refreshServiceCompany();
            })
            .catch((err) => console.error("Update failed", err));
    };

    const handleDelete = () => {
        fetch(`/service-companies/${id}`, {
            method:"DELETE",
            headers: {
                "Authorization": localStorage.getItem("tokenKey"),
            },
        })
            .then((res) => {
                if (res.ok) {
                    refreshServiceCompany();
                }
            })
            .catch((err) => console.error("Delete failed", err));
    }

    return(
        <div className="serviceContainer">
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className={classes.link} to={{pathname : 'companies/' + companyId}}>
                            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                {/*companyName.charAt(0).toUpperCase()*/}
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
                            <Button onClick={handleSave} variant="contained" color="primary">
                                Save
                            </Button>
                        </div>
                    ): (
                        <>
                            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                {price}
                            </Typography>
                            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                {duration}
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
                    {userType && (userType === "COMPANYUSER" || userType === "PRIVATEUSER") &&  (
                        <Button onClick={handleBookingOpen} variant="contained" color="primary" style={{ marginLeft: 'auto' }}>
                            Book Appointment
                        </Button>
                    )}

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