import React, {useEffect, useState} from "react";
import ServiceCompany from "../ServiceCompany/ServiceCompany";
import Container from '@mui/material/Container';
import {makeStyles} from "@mui/styles";
import ServiceCompanyCreateForm from "../ServiceCompany/ServiceCompanyCreateForm";

const useStyles = makeStyles((theme) => ({
    container: {
        display:"flex",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: '#DCCFCF'
    }
}));


function Home(){
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [serviceCompaniesList, setServiceCompaniesList] = useState([]);
    const classes = useStyles();

    const refreshServiceCompany = () => {
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
    }

    useEffect(() => {
        refreshServiceCompany();
    }, [serviceCompaniesList])

    if (error) {
        return <div > Error!!! < /div>;
    } else if (!isLoaded) {
        return <div > Loading... < /div>;
    } else {
        return (

                    <div className={classes.container}>
                        {localStorage.getItem("currentUser") == null? "":
                            <ServiceCompanyCreateForm title={"ddddddd"} companyId={1} companyName={"adasf"} description={"adfasdfasdfadsfasdf"} price={"23"} duration={"40"} refreshServiceCompany={refreshServiceCompany}/> }
                {serviceCompaniesList.map(serviceCompany => (
                    <ServiceCompany title={serviceCompany.name} companyId={serviceCompany.companyId} companyName={serviceCompany.companyName}
                    description={serviceCompany.description} price={serviceCompany.price} duration={serviceCompany.duration}></ServiceCompany>
                ))
                } </div>

        );
    }
}

export default Home;