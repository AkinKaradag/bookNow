import React, {useState} from 'react';
import {Button, FormControl, FormHelperText, Input, InputLabel} from "@mui/material";
import {Link} from "react-router-dom";
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import {postWithoutAuth} from "../APIServices/ApiPost";


function Register(){
    // Zustandsvariablen für Formulardaten und Benutzer-Typ
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        companyName: "",
        companyAddress: "",
        companyCity: "",
        companyPostalCode: "",
        phoneNumber: "",
        description: "",
        userType: "PRIVATEUSER" // Standardwert für Benutzer-Typ

    })

    const options = ['PRIVATEUSER', 'COMPANYUSER'];
    const [inputValue, setInputValue] = React.useState(''); // Zustand für Autocomplete-Feld

    // Handler für Eingabefelder
    const handleInput = (i) => {
        const {name, value} = i.target
        setFormData({
            ...formData,
        [name]: value
        });
    }

    // Funktion zur Registrierung
    const sendRequest = () => {
        const path = formData.userType === "COMPANYUSER" ? "/company" : "/private";

        // Erstellen des Anfragedatensatzes basierend auf dem Benutzer-Typ
        const finalFormData = formData.userType === "COMPANYUSER" ?{
            companyName: formData.companyName,
            companyAddress: formData.companyAddress,
            companyCity: formData.companyCity,
            companyPostalCode: formData.companyPostalCode !== "" ? parseInt(formData.companyPostalCode) : null,
            phoneNumber: formData.phoneNumber,
            description: formData.description,
            password: formData.password,
            userType: formData.userType
        } : {
            name: formData.name,
            email: formData.email,
            password: formData.password,
            userType: formData.userType
        }

        // Senden der Anfrage ohne Authentifizierung
        postWithoutAuth(`auth/register${path}`, finalFormData)
            .then((result) => {
                // Speichern der Daten im Local Storage basierend auf dem Benutzer-Typ
                if(result.userType === 'COMPANYUSER') {
                    localStorage.setItem("id", result.id);
                    localStorage.setItem("companyName", formData.companyName)
                } else {
                    localStorage.setItem("currentUser", result.userId);
                    localStorage.setItem("userName",formData.name);
                }
                localStorage.setItem("tokenKey", result.accessToken)
                localStorage.setItem("refreshKey", result.refreshToken)

                })
            .catch((err) => console.log(err))
    }

    // Handler für den Registrierungsbutton, setzt nach Registrierung alle Felder zurück
    const handleButton = () => {
        sendRequest()
        setFormData({
            name: "",
            email: "",
            password: "",
            companyName: "",
            companyAddress: "",
            companyCity: "",
            companyPostalCode: "",
            phoneNumber: "",
            description: "",
            userType: "PRIVATEUSER"

        });
    }


    return(
        <FormControl>
            {/* Anzeige der Eingabefelder basierend auf dem Benutzer-Typ */}
            {formData.userType === 'COMPANYUSER' ? (
                <>
                <InputLabel style={{top:40}}>Company Name</InputLabel>
                <Input style={{top:40}}
                name="companyName"
                onChange = {handleInput}
                value={formData.companyName}
                />

                <InputLabel style={{top:110}}>Company Address</InputLabel>
                <Input style={{top:60}}
                       name="companyAddress"
                       onChange = {handleInput}
                       value={formData.companyAddress}
                />

                <InputLabel style={{top:175}}>Company City</InputLabel>
                <Input style={{top:80}}
                       name="companyCity"
                       onChange = {handleInput}
                       value={formData.companyCity}
                />

                <InputLabel style={{top:240}}>Company Postal Code</InputLabel>
                <Input style={{top:100}}
                       name="companyPostalCode"
                       onChange = {handleInput}
                       value={formData.companyPostalCode}
                />

                <InputLabel style={{top:310}}>Phone Number</InputLabel>
                <Input style={{top:120}}
                       name="phoneNumber"
                       onChange = {handleInput}
                       value={formData.phoneNumber}
                />

                <InputLabel style={{top:380}}>Description</InputLabel>
                <Input style={{top:140}}
                       name="description"
                       onChange = {handleInput}
                       value={formData.description}
                />

                <InputLabel style={{top:450}}>Password</InputLabel>
                <Input style={{top:160}}
                       name="password"
                       onChange = {handleInput}
                       value={formData.password}
                />


                </>
            ): (
                <>
                <InputLabel style={{top:40}}>Username</InputLabel>
                <Input style={{top:40}}
                name="name"
                onChange = {handleInput}
                value={formData.name}
                />

                <InputLabel style={{top:110}}>E-Mail</InputLabel>
                <Input style={{top:60}}
                       name="email"
                       onChange = {handleInput}
                       value={formData.email}
                />

                    <InputLabel style={{top:180}}>Password</InputLabel>
                    <Input style={{top:80}}
                           name="password"
                           onChange = {handleInput}
                           value={formData.password}
                    />


                </>
            )}
            {/* Auswahlfeld für den Benutzer-Typ */}
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
                renderInput={(params) => <TextField style={{top: 190}} {...params} label="User-Typ" />}
            />

            {/* Registrierungsbutton */}
            <Button variant="contained"
                    style = {{marginTop: 210,
                        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                        color: "white"}}
                    onClick={() => handleButton("register")}>Register</Button>

            {/* Link zur Login-Seite */}
            <FormHelperText style={{margin: 5}}>Are you already registered?</FormHelperText>
            <Link style={{marginTop: 20}} to="/auth/login">Sign in</Link>
        </FormControl>
    )
}

export default Register;
