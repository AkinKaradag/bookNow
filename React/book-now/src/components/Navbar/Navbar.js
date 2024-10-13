import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import {makeStyles} from "@mui/styles";
import {LockOpen} from "@mui/icons-material";

// Definiert die Stile für Links
const useStyle = makeStyles((theme) => ({
    link: {
        textDecoration: "None", // Entfernt die Unterstreichung
        boxShadow: "None",
        color: "white"
    }
}));

function Navbar() {
    const classes = useStyle();
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const userId = localStorage.getItem("currentUser");
    const userType = localStorage.getItem("userType");
    const [navColor, setNavColor] = useState("primary");
    const companyId = localStorage.getItem("companyId");
    const navigate = useNavigate();

    // useEffect-Hook um den Login-Status und die Navigationsfarben festzulegen
    useEffect(() => {
        const currentUserId = userType === "COMPANYUSER" ? companyId : userId;
        const isUserLoggedIn = currentUserId && userType;
        setIsLoggedIn(isUserLoggedIn);

        // Ändert die Navbar-Farbe basierend auf dem Benutzer-Typ
        if (userType === "COMPANYUSER") {
            setNavColor("success"); // Grün für CompanyUser
        } else {
            setNavColor("primary"); // Standardfarbe für PrivateUser
        }

    }, [userType]);

    // Funktion zum Logout des Benutzers und Rückkehr zur Startseite
    const onClick = () => {
        localStorage.clear(); // Löscht den Local Storage
        setIsLoggedIn(false);
        navigate("/"); // Leitet zur Startseite weiter
        window.location.reload(); // Lädt die Seite komplett neu
    };
    // Setzt den Pfad zur Profil-Seite basierend auf dem Benutzer-Typ
    const profilePath = localStorage.getItem("userType") === "COMPANYUSER" ? `/companies/${companyId}` : `/users/${userId}`


    return(


        <div>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static" color={navColor}>
                    <Toolbar>
                        {/* Link zur Startseite */}
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="left">
                            <Link className={classes.link} to="/">Home</Link>
                        </Typography>
                        {/* Link zur Services-Seite */}
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="/services">Services</Link>
                        </Typography>
                        {/* Link zur Service-Erstellungsseite */}
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="/create-service">Create Service</Link>
                        </Typography>
                        {/* Link zur Terminseite */}
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="appointments/">Appointments</Link>
                        </Typography>
                        {/* Login- oder Profil-Link basierend auf dem Login-Status */}
                        <Typography variant="h6" component="div" style={{marginRight: 20, fontSize: 15}}>
                            {!isLoggedIn ? (
                                <Link className={classes.link} to="/auth/login/">Sign in</Link>
                            ) : (
                                <div>
                                    <IconButton className={classes.link} onClick={onClick}>
                                        <LockOpen />
                                    </IconButton>
                                    <Link className={classes.link} to={profilePath}>Profile</Link>
                                </div>
                            )}
                        </Typography>
                        {/* Link zur Registrierungsseite */}
                        <Typography variant="h6" component="div" style={{fontSize: 15}}>
                            <Link className={classes.link} to="/auth/register/">Sign up</Link>
                        </Typography>

                    </Toolbar>
                </AppBar>
            </Box>
        </div>
    )
}

export default Navbar;