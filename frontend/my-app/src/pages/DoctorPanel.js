import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";
import "../components/css/DoctorPanel.css";
import { jwtDecode }  from "jwt-decode";

export default function DoctorPanel() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState("cards");
  const [isListOpen, setIsListOpen] = useState(true);
  const [patients, setPatients] = useState([]);
  const [visits, setVisits] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // TODO: W tym miejscu załaduj dane pacjentów z API.
  // Przykładowe podejście (odkomentuj i dopasuj do swojego apiClienta):
  
  useEffect(() => {
    if (activeTab !== "cards") return;
    setLoading(true);
    apiClient.get("/patient/all")
      .then(res => setPatients(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, [activeTab]);

  useEffect(() => {
    if (activeTab !== "schedule") return;
    loadDoctorVisits();
  }, [activeTab]);


  // Doctor Grafik
  const loadDoctorVisits = async () => {
    try {
      setLoading(true);
      setError(null);
      const token = sessionStorage.getItem("token");
      const decoded = jwtDecode(token);
      const doctorId = decoded.id;  
      const res = await apiClient.get(`/visit/doctor/${doctorId}`);
      setVisits(res.data);
    } catch (err) {
      console.error("Błąd pobierania wizyt:", err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };
  
 async function handlePatientClick(patientId) {
    try {
      setLoading(true);
      setError(null);
      const res = await apiClient.get("/patient/doctorCard", { params: { id: patientId } });
      const patientCard = res.data;
      console.log("Pobrana karta pacjenta dla lekarza:", patientCard);
      // Przekierowujemy do strony szczegółów pacjenta; przekazujemy dane w state,
      // ale strona szczegółów powinna mieć fallback i pobrać dane po id jeśli state jest puste.
      navigate(`/patient/${patientId}`, { state: { patientCard } });
    } catch (err) {
      console.error("Błąd pobierania karty pacjenta:", err);
      setError(err);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="doctor-panel">
      <header>
        <h2>Panel Lekarza</h2>
      </header>

      <nav className="doctor-tabs">
        <button
          onClick={() => {
            setActiveTab("cards");
            setIsListOpen((v) => !v);
          }}
          className={activeTab === "cards" ? "active" : ""}
        >
          Karty Pacjentów
        </button>
        <button
          onClick={() => {
            setActiveTab("schedule");
            setIsListOpen(false);
          }}
          className={activeTab === "schedule" ? "active" : ""  }
         
        >
          Grafik
        </button>
      </nav>

      <section className="doctor-content">
        {activeTab === "cards" && (
          <div>
            <div className="cards-header" style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
              <h3>Karty Pacjentów</h3>
              <button onClick={() => setIsListOpen((v) => !v)}>
                {isListOpen ? "Zwiń" : "Rozwiń"}
              </button>
            </div>

            {isListOpen && (
              <div className="patients-list">
                {loading && <p>Ładowanie pacjentów...</p>}
                {error && <p style={{ color: "red" }}>Błąd: {String(error)}</p>}

                {!loading && patients.length === 0 && !error && (
                  <p>Brak pacjentów (dane niezaładowane). Załaduj listę z API.</p>
                )}

                {!loading && patients.length > 0 && (
                  <ul style={{ listStyle: "none", padding: 0 }}>
             {patients.map((p) => (
                      <li key={p.id} style={{ padding: "6px 0", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                        <button
                          onClick={() => handlePatientClick(p.id)}
                          style={{
                            background: "transparent",
                            border: "none",
                            color: "#083b08",
                            fontWeight: 600,
                            cursor: "pointer",
                            padding: 0,
                            textDecoration: "underline",
                          }}
                          aria-label={`Otwórz kartę pacjenta ${p.firstName} ${p.lastName}`}
                        >
                          {p.firstName} {p.lastName}
                        </button>

                        {/* Mały link jako alternatywa (zachowuje semantykę i możliwość otwarcia w nowej karcie) */}
                        <Link to={`/patient/${p.id}`} style={{ marginLeft: 12, fontSize: 12 }}>
                          Otwórz (bez fetch)
                        </Link>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            )}
          </div>
        )}

        {activeTab === "schedule" && (
          <div>
            <h3>Grafik</h3>
            {loading && <p>Ładowanie wizyt...</p>}
            {error && <p style={{ color: "red" }}>Błąd: {String(error)}</p>}
            {!loading && visits.length === 0 && !error && (
              <p>Brak wizyt w grafiku.</p>
            )}
            {!loading && visits.length > 0 && (
              <table style={{ width: "100%", borderCollapse: "collapse" }}>
                <thead>
                  <tr>
                    <th style={{ border: "1px solid #ddd", padding: "8px" }}>Imię i nazwisko pacjenta</th>
                    <th style={{ border: "1px solid #ddd", padding: "8px" }}>Data wizyty</th>
                    <th style={{ border: "1px solid #ddd", padding: "8px" }}>Godzina wizyty</th>
                  </tr>
                </thead>
                <tbody>
                  {visits.map((visit) => (
                    <tr key={visit.visitId}>
                      <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                        {visit.patientName} {visit.patientSurname}
                      </td>
                      <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                        {visit.date}
                      </td>
                      <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                        {visit.time}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        )}
      </section>
    </div>
  );
}