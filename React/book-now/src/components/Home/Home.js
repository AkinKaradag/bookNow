import React, {useEffect, useState} from "react";
import ServiceCompany from "../ServiceCompany/ServiceCompany";
import Container from '@mui/material/Container';
import {makeStyles} from "@mui/styles";

const useStyles = makeStyles((theme) => ({
    container: {
        display:"flex",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: '#cfe8fc',
        height: '100vh'
    }
}));


function Home(){
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [serviceCompaniesList, setServiceCompaniesList] = useState([]);
    const classes = useStyles();

    useEffect(() => {
        fetch("/service-companies")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setServiceCompaniesList(result);
                },
                (error) => {
                    console.log(error)
                    setIsLoaded(true);
                    setError(error);
                }
            )

    }, [])

    if (error) {
        return <div > Error!!! < /div>;
    } else if (!isLoaded) {
        return <div > Loading... < /div>;
    } else {
        return (

            <div className="container">

                    <Container maxWidth="sm" className={classes.container}>

                {serviceCompaniesList.map(serviceCompany => (
                    <ServiceCompany title={serviceCompany.name} description={serviceCompany.description} price={serviceCompany.price} duration={serviceCompany.duration}></ServiceCompany>
                ))
                } </Container>

            </div>
        );
    }
}

export default Home;