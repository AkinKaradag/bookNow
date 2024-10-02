import React, {useState} from 'react';
import {Button, FormControl, FormHelperText, Input, InputLabel} from "@mui/material";
import user from "../User/User";


function Register(){

    const [username, setUsername] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const handleUsername = (value) => {
        setUsername(value)
    }

    const handleEmail = (value) => {
        setEmail(value)
    }

    const handlePassword = (value) => {
        setPassword(value)
    }

    const sendRequest = (path) => {
        fetch("/auth/"+path, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: username,
                email: email,
                password: password
            }),
        })
            .then((res) => res.json())
            .then((result) => {localStorage.setItem("tokenKey", result.message);
                                    localStorage.setItem("currentUser", result.userId);
                                    localStorage.setItem("userName",username)})
            .then((err) => console.log(err))
    }

    const handleButton = (path) => {
        sendRequest(path)
        setUsername("")
        setEmail("")
        setPassword("")
        console.log(localStorage)
    }


    return(
        <FormControl>
            <InputLabel>Username</InputLabel>
            <Input onChange = {(i) => handleUsername(i.target.value)}/>
            <InputLabel style={{top: 80}}>E-Mail</InputLabel>
            <Input style={{top:40}}
                   onChange = {(i) => handleEmail(i.target.value)}/>
            <InputLabel style={{top: 80}}>Passwort</InputLabel>
            <Input style={{top:40}}
            onChange = {(i) => handlePassword(i.target.value)}/>
            <Button variant="contained"
                style = {{marginTop: 60,
                background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                color: "white"}}
                onClick={() => handleButton("register")}>Register</Button>

            <FormHelperText style={{margin: 20}}>Are you already registered?</FormHelperText>
            <Button variant="contained"
                    style = {{marginTop: 2,
                        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                        color: "white"}}
                        onClick={() => handleButton("login")}>Login</Button>
        </FormControl>
    )
}

export default Register;
