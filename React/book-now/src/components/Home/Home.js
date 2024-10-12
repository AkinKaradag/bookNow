import React from 'react';
import { Button, Box, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Link } from 'react-router-dom';

const useStyles = makeStyles({
    root: {
        height: '60vw', // full screen height
        width: '100vw',
        display: 'flex',
        alignItems: 'flex',
        justifyContent: 'flex-start',
        backgroundImage: `url('/Logo.jpg')`,
        backgroundSize: 'contain',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        color: 'black',
        textAlign: 'center',
        flexDirection: 'column',
        paddingTop: '20px',
    },
    buttonContainer: {
        marginTop: '20px',
        justifyContent: 'center',
        display: 'flex',
        gap: '20px', // space between buttons
    },
    button: {
        color: 'white',
        borderColor: 'white',
    }
});

function Home() {
    const classes = useStyles();

    return (
        <Box className={classes.root}>
            <Typography variant="h2">Willkommen auf unserer Seite!</Typography>
            <Typography variant="h6">Entdecken Sie unsere Dienstleistungen und werden Sie Teil unserer Community.</Typography>
            <Box className={classes.buttonContainer}>
                <Button variant="outlined" className={classes.button} component={Link} to="/services">
                    Our Services
                </Button>
                <Button variant="outlined" className={classes.button} component={Link} to="/auth/register">
                    Sign Up
                </Button>
            </Box>
        </Box>
    );
}

export default Home;
