import React, { useState, useEffect } from "react";
import axios from "axios";
import "../components/css/Doctor.css"; // Import stylów

function Doctors() {
  const [doctors, setDoctors] = useState([]);

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
    
  return (
    <div>
     
       <div className="doctor-list">
      {doctors.map((doctor) => (
        <div className="doctor-card" key={doctor.id}>
          <img
            className="doctor-photo"
            src={`http://localhost:8080/${doctor.photoPath}` } 
            alt={`${doctor.firstName} ${doctor.lastName}`}
          />
          <div className="doctor-info">
            <h3>{doctor.firstName} {doctor.lastName}</h3>
            <p>Wiek: {doctor.age}</p>
            <p>Specjalizacja: {doctor.specialist}</p>
            <p>Doświadczenie: {doctor.experience}</p>
          </div>
        </div>
      ))}
    </div>
      
    </div>
  );
}

export default Doctors;
