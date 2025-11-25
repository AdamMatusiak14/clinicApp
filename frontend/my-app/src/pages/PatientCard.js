import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../components/css/PatientCard.css";
import apiClient from "../components/apiClient.js";




function PatientCard() {

  const [patient, setPatient] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [visits, setVisits] = useState([]);
  const [showVisitsModal, setShowVisitsModal] = useState(false);
  const [expandedVisitId, setExpandedVisitId] = useState(null);
  const navigate = useNavigate();



  useEffect(() => {

    setLoading(true);
    setError(null);

    apiClient.get("/patient/current")
      .then((res) => {
        setPatient(res.data);
        console.log(patient);
      })
      .catch((error) => {
        console.error("Error fetching patient data:", error);
        setError(error);

        if (error.response && error.response.status === 401) {
          navigate("/login");
        }
      })
      .finally(() => {
        setLoading(false);
      });
  }, [navigate]);



  const handlePrescription = () => {
    if (!patient || !patient.patientId) {
      console.warn("No patient id available");
      return;
    }

    apiClient.post("/prescription/findById", null, {
      params: { patientId: patient.patientId }, 
      responseType: "blob",
    })
      .then((res) => {
        const url = window.URL.createObjectURL(new Blob([res.data], { type: "application/pdf" }));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "prescriptions.pdf");
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
      })
      .catch((err) => {
        console.error("Error downloading prescriptions:", err);
      });
  };
  

    const handleVisits = () => {
    if (!patient || !patient.patientId) {
      console.warn("No patient id available for visits");
      return;
    }

    apiClient.post("/visit/findById", null, {
      params: { patientId: patient.patientId },
    })
      .then((res) => {
        setVisits(res.data || []);
        setShowVisitsModal(true);
      })
      .catch((err) => {
        console.error("Error fetching visits:", err);
        if (err.response && err.response.status === 401) navigate("/login");
      });
  };

  const toggleVisitDetails = (id) => {
    setExpandedVisitId(prev => (prev === id ? null : id));
  };


  if (loading) return <div className="patient-card-container">Loading...</div>;
  if (error) return <div className="patient-card-container">Error loading patient data.</div>;
  if (!patient) return <div className="patient-card-container">No patient Data</div>;




  return (
    <div className="patient-card-container">
      <div className="patient-card">
        <h2>{patient.name} {patient.surname}</h2>
        <h3>Informacja dla pacjenta:  </h3>
        <p>{patient.infoPatient}</p>

        <button onClick={handlePrescription }>Prescription</button>
        <button onClick={handleVisits}>Visits</button>

          {showVisitsModal && (
          <div className="modal-overlay" style={{
            position: "fixed", top: 0, left: 0, right: 0, bottom: 0,
            background: "rgba(0,0,0,0.5)", display: "flex", justifyContent: "center", alignItems: "center", zIndex: 1000
          }}>
            <div className="modal-content" style={{ background: "#2f10dbff", padding: 20, maxWidth: 700, width: "90%", borderRadius: 6 }}>
              <h3>Wizyty pacjenta</h3>
              {visits.length === 0 && <p>Brak zaplanowanych wizyt</p>}
              <ul style={{ listStyle: "none", padding: 0 }}>
                {visits.map((v) => (
                  <li key={v.id} style={{ borderBottom: "1px solid #ddd", padding: "8px 0" }}>
                    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                      <div>
                        <strong>{v.date}</strong> &nbsp; {v.time} &nbsp; lek. {v.doctorName || v.doctorFirstName} {v.doctorSurname || v.doctorLastName}
                      </div>
                      <div>
                        <button onClick={() => toggleVisitDetails(v.id)} style={{ marginRight: 8 }}>
                          {expandedVisitId === v.id ? "Ukryj" : "Szczegóły"}
                        </button>
                      </div>
                    </div>
                    {expandedVisitId === v.id && (
                      <div style={{ marginTop: 8, fontStyle: "italic" }}>
                        {v.info || "Brak dodatkowych informacji"}
                      </div>
                    )}
                  </li>
                ))}
              </ul>

              <div style={{ textAlign: "right", marginTop: 12 }}>
                <button onClick={() => setShowVisitsModal(false)}>Zamknij</button>
              </div>
              
               </div>
          </div>
        )}
      </div>
    </div>
  
  );
}


export default PatientCard;
