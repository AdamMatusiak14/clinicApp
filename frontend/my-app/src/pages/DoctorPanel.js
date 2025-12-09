import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import apiClient from "../components/apiClient";
import "../components/css/DoctorPanel.css";

export default function DoctorPanel() {
  const [activeTab, setActiveTab] = useState("cards");
  const [isListOpen, setIsListOpen] = useState(true);
  const [patients, setPatients] = useState([]);
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
  

  // Przykładowe dane do lokalnego testu:
  // useEffect(() => {
  //   setPatients([
  //     { id: 1, firstName: "Jan", lastName: "Kowalski" },
  //     { id: 2, firstName: "Anna", lastName: "Nowak" },
  //   ]);
  // }, []);

  return (
    <div className="doctor-panel">
      <header>
        <h2>Panel Lekarza</h2>
        {/* Możesz tu dodać imię lekarza: {authUser?.firstName} {authUser?.lastName} */}
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
                      <li key={p.id} style={{ padding: "6px 0" }}>
                        <Link to={`/patients/${p.id}`}>
                          {p.firstName} {p.lastName}
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
            <p>Tu wyświetl grafik lekarza — załaduj z API.</p>
          </div>
        )}
      </section>
    </div>
  );
}