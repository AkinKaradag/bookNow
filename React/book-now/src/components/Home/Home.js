import React, {useEffect, useState} from "react";
import ServiceCompany from "../ServiceCompany/ServiceCompany";
import "./Home.scss";

function Home(){
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [serviceCompaniesList, setServiceCompaniesList] = useState([]);

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
                Home!!!

                {serviceCompaniesList.map(serviceCompany => (
                    <ServiceCompany title={serviceCompany.name} description={serviceCompany.description} price={serviceCompany.price} duration={serviceCompany.duration}></ServiceCompany>
                ))
            } </div>
        );
    }
}

export default Home;