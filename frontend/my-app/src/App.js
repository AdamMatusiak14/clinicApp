import logo from './logo.svg';        
import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useEffect } from 'react';
import Menu from "./components/Menu";
import Doctor from "./pages/Doctor";
import PatientMenu from "./pages/PatientMenu";
import Registration from './pages/Registration';
import DataPatient from './pages/DataPatient';
import PatientCard from './pages/PatientCard'; 
import Visits from './pages/Visits';
import Assistant from './pages/Assistant';
import LoginForm from './pages/Login';
import Topbar from './components/Topbar';
import apiClient from './components/apiClient';


function App() {

// useEffect(() => {
//     apiClient.get()
//       .then(res => console.log(res.data))
//       .catch(err => console.log(err));
//   }, []);

  return (
 <Router>
  <Topbar isLoggedIn={!!sessionStorage.getItem("token")} />
  <Routes>
          <Route path="/" element={<Menu/>} />
          <Route path="/patient" element={<PatientMenu/>} />
          <Route path="/doctor" element={<Doctor/>} />
          <Route path="/registration" element={<Registration/>} />
          <Route path="/survey" element={<DataPatient/>} />
          <Route path="/patientCard" element={<PatientCard/>} />
          <Route path="/visits" element={<Visits/>} />
          <Route path="/assistent" element={<Assistant/>} />
          <Route path="/login" element={<LoginForm/>} />
         
    
        </Routes>
    </Router>

  );

   
}

export default App;
