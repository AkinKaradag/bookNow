import React, { useState, useEffect } from 'react';
//import ReactDOM from 'react-dom';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import {Button, InputAdornment, OutlinedInput, styled} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {Link} from "react-router-dom";
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';


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

    const {price, duration, companyId, companyName, refreshServiceCompany} = props;
    const classes = useStyle();
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [isSent, setIsSent] = useState(false);

    const saveServiceCompany = () => {
        fetch("/service-companies",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    name: title,
                    description: description,
                }),
            })
            .then((res) => res.json())
            .catch((err) => console.log("error"))
    }

    const handleSubmit = () => {
        saveServiceCompany();
        setIsSent(true);
        setTitle("");
        setDescription("");
        refreshServiceCompany();
    }

    const handleTitle = (value) => {
        setTitle(value);
        setIsSent(false);
    }

    const handleDescription = (value) => {
        setDescription(value);
        setIsSent(false);
    }

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        isSent(false);
    };

    return(
        <div>
            <Snackbar
                open={isSent}
                autoHideDuration={1200}
                onClose={handleClose}
            >
            <Stack sx={{ width: '100%' }} spacing={2}>
                <Alert variant="outlined" severity="success">
                    This is an outlined success Alert.
                </Alert>
            </Stack>
            </Snackbar>
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className={classes.link} to={{pathname : 'companies/' + companyId}}>
                            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                {companyName.charAt(0).toUpperCase()}
                            </Avatar>
                        </Link>
                    }

                    title={<OutlinedInput id="outlined-adornment-amount"
                    multiline
                    placeholder="Title"
                    inputProps={{maxSize: 55}}
                    fullWidth
                    value = {title}
                    onChange = { (i) => handleTitle(i.target.value)}
                    >

                    </OutlinedInput>}
                    subheader="September 14, 2016"
                />

                <CardContent>
                    <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                        {<OutlinedInput id="outlined-adornment-amount"
                            multiline
                            placeholder="Description"
                            inputProps={{maxSize: 500}}
                            fullWidth
                            value = {description}
                            onChange = { (i) => handleDescription(i.target.value)}
                            endAdornment={
                                <InputAdornment position = "end">
                                <Button
                                    variant="conatined"
                                    style = {{background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                                    color: 'white'}}
                                    onClick={handleSubmit}
                                >Senden</Button>
                                </InputAdornment>
                                        }
                        >
                        </OutlinedInput>}
                    </Typography>
                </CardContent>

            </Card>

        </div>
    )

}

export default ServiceCompanyCreateForm;