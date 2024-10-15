import './App.css';
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import React from "react";
import Home from "./components/Home/Home";
import User from "./components/Profile/User";
import Navbar from "./components/Navbar/Navbar";
import Register from "./components/Auth/Register";
import Login from "./components/Auth/Login";
import Services from "./components/ServiceCompany/Services";
import CreateService from "./components/ServiceCompany/CreateService";
import Appointment from "./components/Appointments/Appointment";
import Company from "./components/Profile/Company";

/**
 * Die App-Komponente ist der Hauptcontainer der Anwendung und steuert die Routing-Logik.
 * Sie integriert die BrowserRouter-Komponente, um die Navigation zwischen verschiedenen Seiten zu ermöglichen.
 * Die Navigation erfolgt über die Navbar-Komponente, und die definierten Routen leiten zu den Hauptansichten der Anwendung weiter:
 * - Home: Startseite der Anwendung
 * - User: Profilseite für private Benutzer
 * - Company: Profilseite für Unternehmensbenutzer
 * - Login und Register: Authentifizierungsseiten für Benutzer
 * - Services: Zeigt die Liste aller Services an
 * - CreateService: Ermöglicht Unternehmensnutzern das Erstellen neuer Services
 * - Appointment: Zeigt Termine an, die Benutzer oder Unternehmen erstellt haben
 *
 * Für die Login- und Register-Routen wird die Authentifizierung geprüft: Falls der Benutzer bereits eingeloggt ist,
 * wird er zur Startseite umgeleitet. Die restlichen Routen sind ohne Authentifizierung erreichbar.
 */


function App() {
    return (
        <div className = "App" >
            <BrowserRouter>
                <Navbar></Navbar>
                <Routes>
                    <Route path="/" element={<Home/>}></Route>
                    <Route path="users/:userId" element={<User/>}></Route>
                    <Route path="companies/:companyId" element={<Company />} />
                    <Route path="/auth/login" element={<Login />}></Route>
                    <Route path="/auth/register" element={<Register />}></Route>

                    <Route path="/services" element={<Services/>}></Route>
                    <Route path="/create-service" element={<CreateService/>}></Route>
                    <Route path="/appointments" element={<Appointment/>}></Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;