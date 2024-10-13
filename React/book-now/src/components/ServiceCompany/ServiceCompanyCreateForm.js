import React, { useState } from 'react';
import {Button, FormControl, InputAdornment, InputLabel, OutlinedInput} from "@mui/material";
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import useApiRequest from "../APIServices/ApiRequest";

/**
 * Die ServiceCompanyCreateForm-Komponente ermöglicht es Company-Usern, eine neuen Service zu erstellen.
 * Das Formular sammelt die erforderlichen Daten wie Name, Beschreibung, Preis und Dauer und sendet diese an das Backend.
 * Nach erfolgreichem Erstellen wird die Liste der Services aktualisiert, und eine Erfolgsmeldung angezeigt.
 */


// Die Komponente für das Erstellen eines neuen Services
function ServiceCompanyCreateForm(props) {

    const {refreshServiceCompany} = props; // Funktion zum Aktualisieren der Service-Liste
    const [isSent, setIsSent] = useState(false); // Zustand für die Anzeige des Bestätigungsdialogs
    const companyId = localStorage.getItem("companyId"); // Company-ID aus Local Storage abrufen
    const { post } = useApiRequest(); // Zugriff auf die POST-Funktion aus der zentralen API-Logik

    // State für die Eingabefelder im Formular
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        price: 0,
        duration: 0
    })

    // Handler für die Eingabeänderungen
    const handleInput = (i) => {
        const {name, value} = i.target;
        setFormData({
            ...formData,
            [name]: value
        });
        setIsSent(false); // Bestätigungsdialog zurücksetzen
    }

    // Handler für das Absenden des Formulars
    const handleSubmit = () => {
        // Daten zum Absenden an das Backend vorbereiten
        const dataToSend = {
            ...formData,
            companyId: companyId
        };

        // Anfrage zum Erstellen eines neuen Services
        post("service-companies", dataToSend)
            .then(() => {
                setIsSent(true); // Erfolgreich gesendet, Bestätigungsdialog anzeigen
                setFormData({ // Eingabefelder zurücksetzen
                    name: "",
                    description: "",
                    price: '',
                    duration: ''
                });
                refreshServiceCompany(); // Service-Liste aktualisieren
            })
            .catch((err) => console.log("error:", err));
    };

    // Handler zum Schliessen des Bestätigungsdialogs
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
                    {/* Eingabefeld für den Namen des Services */}
                    <TextField
                        name="name"
                        label="name"
                        value={formData.name}
                        onChange={handleInput}
                        sx={{m: 1, width: '25ch'}}
                    />
                    {/* Eingabefeld für die Beschreibung des Services */}
                    <TextField
                        name="description"
                        label="description"
                        multiline
                        rows={4}
                        value={formData.description}
                        onChange={handleInput}
                        sx={{m: 1, width: '25ch'}}
                    />

                    {/* Eingabefeld für den Preis des Services */}
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

                    {/* Eingabefeld für die Dauer des Services */}
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

                    {/* Button zum Absenden des Formulars */}
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

            {/* Snackbar für Erfolgsnachricht */}
            <Snackbar open={isSent} autoHideDuration={3000} onClose={handleClose}>
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                    Service erfolgreich hinzugefügt!
                </Alert>
            </Snackbar>

        </div>
    )
}
export default ServiceCompanyCreateForm;