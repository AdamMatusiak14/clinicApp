import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../components/css/PatientCard.css";



function PatientCard() {

  const [patientCard, setCard] = useState([]);
  const navigate = useNavigate();

  const [showVisitsModal, setShowVisitsModal] = useState(false);
  const [visits, setVisits] = useState([]);

  useEffect(() => {
   const patientId = Number(sessionStorage.getItem("patientId"));
   axios.get("http://localhost:8080/patient/card", {params: { id: patientId }})
   .then((response) => {
     setCard(response.data);
     console.log(response.data);
   })
  }
  , []);

  const handlePrescription = () => {
    const patientId = Number(sessionStorage.getItem("patientId"));
    axios
      .post("http://localhost:8080/prescription/findById", null, {
        
  params: { patientId },
    responseType: "blob",
  }).then((res) => {
    const url = window.URL.createObjectURL(new Blob([res.data], { type: "application/pdf" }));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "prescriptions.pdf");
    document.body.appendChild(link);
    link.click();
    link.remove();
      });
  };

  const handleVisits = () => {
    const patientId = Number(sessionStorage.getItem("patientId"));
    axios
      .post("http://localhost:8080/api/visit/findById", null, {
        params: { patientId }
  }).then((res) => {
    setVisits(res.data);
    setShowVisitsModal(true);
    console.log(res.data);
  });
  };

    
  return (
    <div className="patient-card-container">
      <div className="patient-card">
      <p><h2> {patientCard.name} {patientCard.surname}</h2></p>
      <h3>Informacja dla pacjenta:  </h3>
       <p> {patientCard.infoPatient}</p>

    <button onClick={handlePrescription}>Prescription</button>
    <button onClick={handleVisits}>Visits</button>
    {showVisitsModal && (
    <div className="modal-overlay">
     <div className="modal-content">
       <h3>Wizyty pacjenta</h3>
       <ul>
        {visits.map((v, index) => (
          <li key={index}>
           <span style={{ marginRight: 10 }}> {v.date} </span>
            <span style={{ marginRight: 10 }}> {v.time} </span>
            <span style={{ marginRight: 10 }}> lek. {v.doctorName} {v.doctorSurname} </span>
             <div style={{ marginTop: 4, fontStyle: "italic" }}>{v.info}</div>
          </li>
        ))}
        </ul>
       <button onClick={() => setShowVisitsModal(false)}>Zamknij</button>
      </div>
     </div>
)}
    </div>
    </div>
  
  );
}


export default PatientCard;
