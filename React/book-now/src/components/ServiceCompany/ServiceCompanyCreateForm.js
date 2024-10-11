import React, { useState, useEffect } from 'react';
//import ReactDOM from 'react-dom';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import {Button, FormControl, FormHelperText, InputAdornment, InputLabel, OutlinedInput, styled} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {Link} from "react-router-dom";
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";


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


function ServiceCompanyCreateForm(props) {

    const {refreshServiceCompany} = props;
    const classes = useStyle();
    const [isSent, setIsSent] = useState(false);


    const [formData, setFormData] = useState({
        name: "",
        description: "",
        price: 0,
        duration: 0
    })

    const handleInput = (i) => {
        const {name, value} = i.target;
        setFormData({
            ...formData,
            [name]: value
        });
        setIsSent(false);
    }


    const handleSubmit = () => {
        const companyId = localStorage.getItem("companyId");
        const token = localStorage.getItem("tokenKey");
        console.log("Token:", token);
        const dataToSend = {
            ...formData,
            companyId: companyId
        };
        fetch("/service-companies",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                },
                body: JSON.stringify(dataToSend),
            })
            .then((res) => {
                console.log("Response status:", res.status); // Protokolliere den Statuscode
                console.log("Response headers:", res.headers); // Protokolliere die Header
                return res.text(); // Verwende .text() um den Antworttext zu erhalten
            })
            .then((text) => {
                console.log("Response body as text:", text); // Ausgabe des Textes zur Fehleranalyse
                // Versuche, den Text zu JSON zu parsen, falls die Antwort erwartet wird
                if (text) {
                    return JSON.parse(text);
                } else {
                    throw new Error("Leere Antwort erhalten");
                }
            })
            .then((json) => {
                console.log("Parsed JSON:", json);
                setIsSent(true);
                setFormData({
                    name: "",
                    description: "",
                    price: '',
                    duration: ''
                });
                refreshServiceCompany();
            })
            .catch((err) => console.log("error:", err));
    };


    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setIsSent(false);
    };

    return(
        <div>
            <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
                <div>

                    <TextField
                        name="name"
                        label="name"
                        value={formData.name}
                        onChange={handleInput}
                        sx={{m: 1, width: '25ch'}}
                    />

                    <TextField
                        name="description"
                        label="description"
                        multiline
                        rows={4}
                        value={formData.description}
                        onChange={handleInput}
                        sx={{m: 1, width: '25ch'}}
                    />


                    <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined">
                        <InputLabel htmlFor={"outlined-adornment-price"}>Price</InputLabel>
                        <OutlinedInput
                            id="outlined-adornment-price"
                            name="price"
                            value={formData.price}
                            onChange={handleInput}
                            startAdornment={<InputAdornment position="start">CHF</InputAdornment>}
                            label="price"
                            type="number"
                        />
                    </FormControl>

                    <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined">
                        <InputLabel htmlFor={"outlined-adornment-duration"}>Duration</InputLabel>
                        <OutlinedInput
                            id="outlined-adornment-duration"
                            name="duration"
                            value={formData.duration}
                            onChange={handleInput}
                            startAdornment={<InputAdornment position="start">min</InputAdornment>}
                            label="duration"
                            type="number"
                        />
                    </FormControl>

                    <InputAdornment position = "end">
                        <Button
                            variant="contained"
                            style = {{background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                                color: 'white'}}
                            onClick={handleSubmit}
                        >Senden</Button>
                    </InputAdornment>
                </div>

            </Box>

            <Snackbar open={isSent} autoHideDuration={3000} onClose={handleClose}>
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                    Service erfolgreich hinzugefügt!
                </Alert>
            </Snackbar>

        </div>
    )

}

export default ServiceCompanyCreateForm;