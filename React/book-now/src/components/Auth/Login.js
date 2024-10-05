import React, {useState} from 'react';
import {Button, FormControl, FormHelperText, Input, InputLabel} from "@mui/material";
import {Link, Navigate, useNavigate} from "react-router-dom";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";


function Login(){

    const [formData, setFormData] = useState({
        name: "",
        companyName: "",
        password: "",
        userType: "PRIVATEUSER"
    })

    const options = ['PRIVATEUSER' , 'COMPANYUSER'];
    const [inputValue, setInputValue] = React.useState('');

    const handleInput = (i) => {
        const {name, value} = i.target
        setFormData({
            ...formData,
            [name]: value
        });
    }

    let navigate = useNavigate();

    const sendRequest = () => {
        const path = formData.userType === "COMPANYUSER" ? "/company" : "/private";

        const finalFromData= formData.userType === "COMPANYUSER" ?{
            companyName: formData.companyName,
            password: formData.password,
            userType: formData.userType
        } : {
            name: formData.name,
            password: formData.password,
            userType: formData.userType
        }
        fetch("/auth/login" + path, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(finalFromData),
        })
            .then((res) => res.json())
            .then((result) => {
                if(result.userType === 'COMPANYUSER') {
                    localStorage.setItem("companyId", result.companyId);
                    localStorage.setItem("companyName", formData.companyName)
                } else {
                    localStorage.setItem("currentUser", result.userId);
                    localStorage.setItem("userName", formData.name)
                }
                localStorage.setItem("tokenKey", result.message);


                navigate("/");
            })
            .catch((err) => console.log(err))
    }

    const handleButton = (path) => {
        sendRequest(path)
        setFormData({
            name: "",
            companyName: "",
            password: "",
            userType: "PRIVATEUSER"
        });
    }


    return(
        <FormControl style={{top: 10}}>
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
                    onChange={handleInput}
                    value={formData.password}
                    />
                </>
            )}

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


            <FormHelperText style={{marginTop: 150}}></FormHelperText>
            <Button variant="contained"
                    style = {{marginTop: 15,
                        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 60%)',
                        color: "white"}}
                    onClick={() => handleButton("login")}>Login</Button>
            <FormHelperText style={{margin: 20}}>Don't have an Account?</FormHelperText>
            <Link style={{marginTop: 20}} to="/auth/register">Sign up</Link>
        </FormControl>
    )
}

export default Login;
