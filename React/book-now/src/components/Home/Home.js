import React from 'react';
import { Button, Box, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Link } from 'react-router-dom';

// Definieren von Styles mit makeStyles-Hook
const useStyles = makeStyles({
    root: {
        height: '60vw', // Höhe des Bildschirms für ein responsives Layout
        width: '100vw', // Volle Bildschirmbreite
        display: 'flex',
        alignItems: 'flex', // Elemente vertikal ausrichten
        justifyContent: 'flex-start', // Inhalte nach links ausrichten
        backgroundImage: `url('/Logo.jpg')`, // Setzen eines Hintergrundbildes
        backgroundSize: 'contain', // Bildgrösse anpassen
        backgroundRepeat: 'no-repeat', // Keine Wiederholung des Bildes
        backgroundPosition: 'center', // Bild in der Mitte positionieren
        color: 'black', // Textfarbe schwarz
        textAlign: 'center',
        flexDirection: 'column', // Elemente vertikal anordnen
        paddingTop: '20px', // Abstand nach oben für eine bessere Ausrichtung
    },
    buttonContainer: {
        marginTop: '20px', // Abstand nach oben
        justifyContent: 'center', // Buttons zentrieren
        display: 'flex',
        gap: '20px', // Abstand zwischen den Buttons
    },
    button: {
        color: 'white', // Textfarbe der Buttons weiss
        borderColor: 'white', // Rahmenfarbe der Buttons weiss
    }
});

function Home() {
    const classes = useStyles(); // Stile verwenden

    return (
        <Box className={classes.root}>
            {/* Begrüssungstext */}
            <Typography variant="h2">Willkommen auf unserer Seite!</Typography>
            <Typography variant="h6">Entdecken Sie unsere Dienstleistungen und werden Sie Teil unserer Community.</Typography>

            {/* Button-Container für die Navigation */}
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
