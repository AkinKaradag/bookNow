//import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import React from "react";
import Home from "./components/Home/Home";
import User from "./components/User/User";
import Navbar from "./components/Navbar/Navbar";
import Register from "./components/Auth/Register";
import Login from "./components/Auth/Login";
import ServiceCompany from "./components/ServiceCompany/ServiceCompany";

function App() {
    return (
        <div className = "App" >
            <BrowserRouter>
                <Navbar></Navbar>
                <Routes>
                    <Route path="/" element={<Home/>}></Route>
                    <Route path="users/:userId" element={<User/>}></Route>
                    <Route path="/auth/login" element={localStorage.getItem("currentUser") != null ? <Navigate to="/"/>: <Login/>}>
                </Route>
                    <Route path="/auth/register" element={localStorage.getItem("currentUser") != null ? <Navigate to="auth/login"/>: <Register/>}>
                    </Route>
                    <Route path="service-companies/" element={<ServiceCompany/>}></Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;