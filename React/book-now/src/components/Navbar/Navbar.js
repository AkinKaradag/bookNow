import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import {makeStyles} from "@mui/styles";
import {LockOpen} from "@mui/icons-material";


const useStyle = makeStyles((theme) => ({
    link: {
        textDecoration: "None",
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


    useEffect(() => {
        const currentUserId = userType === "COMPANYUSER" ? localStorage.getItem("companyId") : localStorage.getItem("currentUser");
        const isUserLoggedIn = currentUserId && userType;
        setIsLoggedIn(isUserLoggedIn);
        console.log("Checking login status...");
        console.log("currentUser:", currentUserId);
        console.log("userType:", userType);

        // Change Navbar color based on user type
        if (userType === "COMPANYUSER") {
            setNavColor("success"); // Green for CompanyUser
        } else {
            setNavColor("primary"); // Default color for PrivateUser
        }

    }, [userType]);


    const onClick = () => {
        localStorage.clear();
        setIsLoggedIn(false);
        navigate("/");
        window.location.reload(); // Reset the page completely

    }



    const profilePath = localStorage.getItem("userType") === "COMPANYUSER" ? `/companies/${localStorage.getItem("companyId")}` : `/users/${localStorage.getItem("currentUser")}`

    console.log("Is Logged In:", isLoggedIn);

    return(


        <div>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static" color={navColor}>
                    <Toolbar>

                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="left">
                            <Link className={classes.link} to="/">Home</Link>
                        </Typography>

                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="/services">Services</Link>
                        </Typography>

                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="/create-service">Create Service</Link>
                        </Typography>

                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="Center">
                            <Link className={classes.link} to="appointments/">Appointments</Link>
                        </Typography>

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