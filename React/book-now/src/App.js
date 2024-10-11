//import logo from './logo.svg';
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

function App() {
    return (
        <div className = "App" >
            <BrowserRouter>
                <Navbar></Navbar>
                <Routes>
                    <Route path="/" element={<Home/>}></Route>
                    <Route path="users/:userId" element={<User/>}></Route>
                    <Route path="/auth/login/" element={localStorage.getItem("currentUser") != null ? <Navigate to="/"/>: <Login/>}>
                </Route>
                    <Route path="/auth/register/" element={localStorage.getItem("currentUser") != null ? <Navigate to="auth/login"/>: <Register/>}>
                    </Route>
                    <Route path="/services" element={<Services/>}></Route>
                    <Route path="/create-service" element={<CreateService/>}></Route>
                    <Route path="/appointments" element={<Appointment/>}></Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;