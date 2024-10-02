import React, {useState} from 'react';
import {Button, FormControl, FormHelperText, Input, InputLabel} from "@mui/material";


function Login(){

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const handleUsername = (value) => {
        setUsername(value)
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
        setPassword("")
        console.log(localStorage)
    }


    return(
        <FormControl>
            <InputLabel>Username</InputLabel>
            <Input onChange = {(i) => handleUsername(i.target.value)}/>
            <InputLabel style={{top: 80}}>Passwort</InputLabel>
            <Input style={{top:40}}
                   onChange = {(i) => handlePassword(i.target.value)}/>


            <FormHelperText style={{margin: 20}}>Bitte Einloggen</FormHelperText>
            <Button variant="contained"
                    style = {{marginTop: 2,
                        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                        color: "white"}}
                    onClick={() => handleButton("login")}>Login</Button>
        </FormControl>
    )
}

export default Login;
