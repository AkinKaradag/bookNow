import React, {useEffect, useState} from 'react';
import ServiceCompanyCreateForm from "./ServiceCompanyCreateForm";
import ServiceCompany from "./ServiceCompany";
import {makeStyles} from "@mui/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";

const useStyle = makeStyles((theme) => ({
    container: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    },
    servicesList: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: '100%'
    },
    root: {
        width: 800,
        textAlign: "left",
        margin: 20
    }
}));


function CreateService() {
    const classes = useStyle();
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const userType = localStorage.getItem("userType");

    console.log("UserType:", userType);

    // Fetch services for the logged-in company
    const fetchServices = () => {
        const companyId = localStorage.getItem("companyId");
        fetch(`/service-companies?companyId=${companyId}`, {
            headers: {
                "Authorization": localStorage.getItem("tokenKey")
            }
        })
            .then((res) => res.json())
            .then((result) => {
                console.log("API Response: ", result)
                setServices(result);
                setLoading(false);
            })
            .catch((err) => {
                console.log("Error fetching services:", err);
                setLoading(false);
            });
    };

    const refreshServiceCompany = () => {
        fetchServices()
    }

    useEffect(() => {
        fetchServices();
    }, []);

    return (
        <div className={classes.container}>
            <h2>Create a New Service</h2>

            <p>User Type is: {userType}</p>

            {userType && userType.toUpperCase() === "COMPANYUSER" ? (
                <Card className={classes.root}>
                    <CardHeader title="Create a New Service" />
                    <CardContent>
                        <ServiceCompanyCreateForm refreshServiceCompany={refreshServiceCompany} />
                    </CardContent>
                </Card>
            ):(
                <p>Nur Company-User können Servies hinzufügen</p>
            )}

            <h2>Company Services</h2>

            {/* Display list of services */}
            <div className={classes.servicesList}>
                {loading ? (
                    <div>Loading services...</div>
                ) : (
                    services.length > 0 ? (
                        services.map((service) => (
                            <ServiceCompany
                                key={service.id}
                                serviceId={service.serviceId}
                                title={service.name}
                                description={service.description}
                                price={service.price}
                                duration={service.duration}
                                companyId={service.companyId}
                                companyName={service.companyName}
                                refreshServiceCompany={refreshServiceCompany}
                            />
                        ))
                    ) : (
                        <div>No services available.</div>
                    )
                )}
            </div>
        </div>
    );
}

export default CreateService;