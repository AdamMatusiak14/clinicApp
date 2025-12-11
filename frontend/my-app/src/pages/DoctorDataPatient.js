import React, { useEffect, useState } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";
import "../components/css/PatientCard.css"; // korzystamy ze stylów podobnych do PatientCard

export default function DoctorDataPatient() {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  // Przyjmujemy patientCard z location.state jeśli jest, inaczej null
  const [patient, setPatient] = useState(location.state?.patientCard || null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Jeśli nie mamy danych (np. direct link / odświeżenie), fetchujemy po id
  useEffect(() => {
    if (patient) return; // mamy już dane
    if (!id) {
      setError("Brak identyfikatora pacjenta.");
      return;
    }

    setLoading(true);
    apiClient
      .get("/patient/card", { params: { id } })
      .then((res) => {
        setPatient(res.data);
      })
      .catch((err) => {
        console.error("Błąd pobierania karty pacjenta:", err);
        setError(err?.response?.data || err.message || String(err));
      })
      .finally(() => setLoading(false));
  }, [id, patient]);

  if (loading) {
    return <div className="patient-card-container"><div className="patient-card"><p>Ładowanie danych pacjenta...</p></div></div>;
  }

  if (error) {
    return <div className="patient-card-container"><div className="patient-card"><p style={{ color: "red" }}>Błąd: {String(error)}</p></div></div>;
  }

  if (!patient) {
    return <div className="patient-card-container"><div className="patient-card"><p>Brak danych pacjenta do wyświetlenia.</p></div></div>;
  }

  // Obsługa różnych nazw pola id (czasem backend zwraca `id`, innym razem `patientId`)
  const patientId = patient.id ?? patient.patientId;

  return (
    <div className="patient-card-container">
      <div className="patient-card">
        <h2>{patient.firstName} {patient.lastName}</h2>
        <p><strong>ID:</strong> {patientId}</p>
        <p><strong>Wiek:</strong> {patient.age ?? "—"}</p>
        <p><strong>Płeć:</strong> {patient.sex ?? "—"}</p>
        <p style={{ whiteSpace: "pre-wrap" }}><strong>Informacje:</strong> {patient.infoPatient ?? "—"}</p>

        <hr />

        <h3>Stan zdrowia i historia</h3>
        <p><strong>Leki przyjmowane:</strong> {patient.takingMedication ?? "Brak danych"}</p>
        <p><strong>Przebyte choroby:</strong> {patient.pastIllnesses ?? "Brak danych"}</p>
        <p><strong>Choroby przewlekłe:</strong> {patient.chronicDiseases ?? "Brak danych"}</p>
        <p><strong>Szczepienia:</strong> {patient.vaccinations ?? "Brak danych"}</p>
        <p><strong>Alergie:</strong> {patient.allergies ?? "Brak danych"}</p>
        <p><strong>Historia rodzinna:</strong> {patient.familyHistory ?? "Brak danych"}</p>
        <p><strong>Palacz:</strong> {patient.smoking ?? "—"}</p>
        <p><strong>Alkohol:</strong> {patient.alcohol ?? "—"}</p>

        <div style={{ display: "flex", gap: 12, marginTop: 16 }}>
          <button
            style={{
              padding: "10px 14px",
              borderRadius: 6,
              border: "none",
              background: "#0c580c",
              color: "#ffd600",
              fontWeight: 700,
              cursor: "pointer"
            }}
            onClick={() => {
              // Przykładowy handler: tutaj możesz otworzyć edycję lub przesłać dane dalej
              alert("Tu możesz otworzyć edycję pacjenta (implementuj według potrzeb).");
            }}
          >
            Edytuj
          </button>

          <button
            style={{
              padding: "10px 14px",
              borderRadius: 6,
              border: "1px solid #0c580c",
              background: "transparent",
              color: "#083b08",
              fontWeight: 700,
              cursor: "pointer"
            }}
            onClick={() => navigate(-1)}
          >
            Powrót
          </button>
        </div>
      </div>
    </div>
  );
}