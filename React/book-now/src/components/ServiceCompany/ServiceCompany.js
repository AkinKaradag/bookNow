import React, { useState, useEffect } from 'react';
//import ReactDOM from 'react-dom';


function ServiceCompany() {
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
            <ul> {
                serviceCompaniesList.map(serviceCompany => (
                    <li>
                        {serviceCompany.name} {serviceCompany.description} {serviceCompany.price} {serviceCompany.duration}
                </li>
                ))
            }
            </ul>
        );
    }
}

export default ServiceCompany;