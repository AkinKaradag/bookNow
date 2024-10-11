import React, {useEffect, useState} from "react";
import ServiceCompany from "../ServiceCompany/ServiceCompany";
import Container from '@mui/material/Container';
import {makeStyles} from "@mui/styles";
import ServiceCompanyCreateForm from "./ServiceCompanyCreateForm";

const useStyles = makeStyles((theme) => ({
    container: {
        display:"flex",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: '#DCCFCF'
    }
}));


function Services(){
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [serviceCompaniesList, setServiceCompaniesList] = useState([]);
    const [refresh, setRefresh] = useState(false)
    const classes = useStyles();

    const refreshServiceCompany = () => {
        fetch("/service-companies")
            .then(res => res.json())
            .then(
                (result) => {
                    //console.log("Backend Response: ", result);
                    setIsLoaded(true);
                    setServiceCompaniesList(result);
                },
                (error) => {
                    console.log(error)
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }

    useEffect(() => {
        refreshServiceCompany();
    }, []);

    if (error) {
        return <div > Error!!! < /div>;
    } else if (!isLoaded) {
        return <div > Loading... < /div>;
    } else {
        return (

            <div className={classes.container}>

                {serviceCompaniesList.map(serviceCompany => (
                    <ServiceCompany key={serviceCompany.serviceId} title={serviceCompany.name} serviceId={serviceCompany.serviceId}
                                    companyName={serviceCompany.companyName} companyId={serviceCompany.companyId}
                                    description={serviceCompany.description} price={serviceCompany.price} duration={serviceCompany.duration}></ServiceCompany>
                ))
                } </div>

        );
    }
}

export default Services;