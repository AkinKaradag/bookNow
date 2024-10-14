import React, {useState} from 'react';
import {Button, FormControl, FormHelperText, Input, InputLabel} from "@mui/material";
import {Link, useNavigate} from "react-router-dom";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import useApiRequest from "../APIServices/ApiRequest";


function Login(){
    // Zustandsvariablen für Formulardaten, Fehlermeldungen und Navigation
    const [formData, setFormData] = useState({
        name: "",
        companyName: "",
        password: "",
        userType: "PRIVATEUSER" // Standardwert für den Benutzer-Typ
    });

    const options = ['PRIVATEUSER' , 'COMPANYUSER'];
    const [inputValue, setInputValue] = React.useState(''); // Zustand für den Text im Autocomplete-Feld
    const [error, setError] = useState(''); // Zustand für Fehlermeldungen
    const navigate = useNavigate(); // Verwenden des useNavigate-Hooks zur Navigation

    // Importiert die Funktion post aus ApiRequest
    const { post } = useApiRequest();

    // Funktion zum Aktualisieren der Eingabefelder
    const handleInput = (i) => {
        const {name, value} = i.target
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Funktion zur Absendung der Login-Daten
    const sendRequest = () => {
        const path = formData.userType === "COMPANYUSER" ? "auth/login/company" : "auth/login/private";

        // Konfiguration der Anfragedaten abhängig vom Benutzer-Typ
        const finalFromData= formData.userType === "COMPANYUSER" ?{
            companyName: formData.companyName,
            password: formData.password,
            userType: formData.userType
        } : {
            name: formData.name,
            password: formData.password,
            userType: formData.userType
        }

        // Überprüfen, ob alle erforderlichen Felder ausgefüllt sind
        if ((!formData.name && formData.userType === "PRIVATEUSER") || (!formData.companyName && formData.userType === "COMPANYUSER") || !formData.password) {
            setError("Please fill in all the required fields.");
            return;
        }

        // Anfrage ohne Authentifizierung senden
        post(path, finalFromData)
            .then((result) => {
                // Speichern der relevanten Daten im Local Storage je nach Benutzer-Typ
                if(result.userType === 'COMPANYUSER') {
                    localStorage.setItem("companyId", result.id);
                    localStorage.setItem("companyName", formData.companyName);
                    localStorage.setItem("userType", result.userType);
                } else {
                    localStorage.setItem("currentUser", result.id);
                    localStorage.setItem("userName", formData.name);
                    localStorage.setItem("userType", result.userType);
                }
                if(result.accessToken && result.refreshToken) {
                localStorage.setItem("tokenKey", result.accessToken);
                localStorage.setItem("refreshKey", result.refreshToken);
                navigate("/");
                } else {
                    navigate("/login")
                }

                navigate("/"); // Navigation zur Startseite nach erfolgreichem Login
            })
            .catch((err) => {
                console.log(err);
                setError("Login failed. Please check your credentials.");
            })
    }

    // Button-Klick-Handler zur Auslösung des Login-Requests
    const handleButton = () => {
        sendRequest()
    }


    return(
        <FormControl style={{top: 10}}>
            {/* Anzeige von Eingabefeldern basierend auf dem Benutzer-Typ */}
            {formData.userType === 'COMPANYUSER' ? (
                <>
                <InputLabel style={{top:40}}>Company Name</InputLabel>
                <Input
                style={{top: 40}}
                name="companyName"
                onChange = {handleInput}
                value={formData.companyName}
                />

                    <InputLabel style={{top:110}}>Passwort</InputLabel>
                    <Input
                        style={{top:60}}
                        name="password"
                        type="password"
                        onChange={handleInput}
                        value={formData.password}
                    />

                </>
            ): (
                <>
                    <InputLabel style={{top:40}}>Username</InputLabel>
                    <Input
                    style={{top:40}}
                    name="name"
                    onChange={handleInput}
                    value={formData.name}
                    />

                    <InputLabel style={{top:110}}>Passwort</InputLabel>
                    <Input
                    style={{top:60}}
                    name="password"
                    type="password"
                    onChange={handleInput}
                    value={formData.password}
                    />
                </>
            )}
            {/* Auswahlfeld für Benutzer-Typ */}
            <Autocomplete
                value={formData.userType}
                onChange={(event, newValue) => {
                    setFormData({
                        ...formData,
                        userType: newValue
                    });
                }}
                inputValue={inputValue}
                onInputChange={(event, newInputValue) => {
                    setInputValue(newInputValue);
                }}
                id="controllable-states-demo"
                options={options}
                sx={{ width: 300 }}
                renderInput={(params) => <TextField style={{top: 130}} {...params} label="User-Typ" />}
            />

            {/* Anzeige von Fehlermeldungen */}
            {error && <FormHelperText error style={{marginTop: 150}}>{error}</FormHelperText>}

            {/* Login-Button */}
            <Button variant="contained"
                    style = {{marginTop: 155,
                        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                        color: "white"}}
                    onClick={() => handleButton("login")}>Login</Button>

            {/* Link zur Registrierungsseite */}
            <FormHelperText style={{margin: 20}}>Don't have an Account?</FormHelperText>
            <Link style={{marginTop: 20}} to="/auth/register">Sign up</Link>
        </FormControl>
    )
}

export default Login;
