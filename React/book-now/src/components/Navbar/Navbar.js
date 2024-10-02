import React from "react";
import {Link, useHistory} from "react-router-dom";
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
    let history = useHistory();

    const onClick = () => {
        localStorage.removeItem("tokenKey")
        localStorage.removeItem("currentUser")
        localStorage.removeItem("userName")
        history.go(0)
    }

    return(


        <div>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar>
                        <IconButton
                            size="large"
                            edge="start"
                            color="inherit"
                            aria-label="menu"
                            sx={{ mr: 2 }}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} textAlign="left">
                            <Link className={classes.link} to="/">Home</Link>
                        </Typography>
                        <Typography variant="h6" component="div">
                            {localStorage.getItem("currentUser") == null ? <Link className={classes.link} to="/auth">Sign in</Link>:
                               <div><IconButton className={classes.link} onClick={onClick}><LockOpen></LockOpen></IconButton>
                        <Link className={classes.link} to={{pathname : 'users/' + localStorage.getItem("currentUser")}}>Profile</Link>
                               </div>}
                        </Typography>

                    </Toolbar>
                </AppBar>
            </Box>
        </div>
    )
}

export default Navbar;