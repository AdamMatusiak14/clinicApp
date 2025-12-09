import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../components/css/Doctor.css"; // Import stylów
import {jwtDecode} from "jwt-decode";
import { useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";

function Doctors() {
  const [doctors, setDoctors] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get("http://localhost:8080/api/doctors")
    .then((response) => {
      console.log("To przychodzi z backendu", response.data); // sprawdź co zwraca backend
      setDoctors(response.data);  // ustaw dane w stanie
    })
    .catch((error) => {
      console.error("Błąd podczas pobierania lekarzy:", error);
    });
}, []);

  
  

  const getAuthFromToken = () => {
    try{
    const token = sessionStorage.getItem("token");
    if(!token) return null;
    const auth = jwtDecode(token);
    console.log("Decoded auth from token:", auth);
    return auth;
    } catch (error) {
      console.error("Błąd podczas dekodowania tokena:", error);
      return null;
    }

  };


    const handleDoctorClick = async () => {
    const token = sessionStorage.getItem("token");
    if (!token) return;

    try {
      const res = await apiClient.get("/doctor/panel");
      console.log("Odpowiedź z /doctor/panel:", res);
      if (res.status === 200) {
        console.log("Dostęp do panelu lekarza potwierdzony");
        navigate("/doctorPanel");
      }
    } catch (err) {
      console.error("Brak dostępu lub błąd backendu", err);
    }
  };

  const authUser = getAuthFromToken();
    
  return (
    <div>
      <div className="doctor-list">
        {doctors.map((doctor) => {
          // sprawdź czy zalogowany użytkownik jest lekarzem i czy to jego karta
          const isDoctorRole =
            authUser &&
            (authUser.role === "ROLE_DOCTOR" );
        
           const canClick = isDoctorRole  

          const CardInner = (
            <>
              <img
                className="doctor-photo"
                src={`http://localhost:8080/${doctor.photoPath}`}
                alt={`${doctor.firstName} ${doctor.lastName}`}
              />
              <div className="doctor-info">
                <h3>{doctor.firstName} {doctor.lastName}</h3>
                <p>Wiek: {doctor.age}</p>
                <p>Specjalizacja: {doctor.specialist}</p>
                <p>Doświadczenie: {doctor.experience}</p>
              </div>
            </>
          );

          return (
            <div className="doctor-card-wrapper" key={doctor.id}>

              {canClick ? (
              <div className="doctor-card" 
              onClick={handleDoctorClick} style={{ cursor: 'pointer' }}> 
                    {CardInner}
                  </div>
              ) : (
                <div className="doctor-card">
                  {CardInner}
                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Doctors;
