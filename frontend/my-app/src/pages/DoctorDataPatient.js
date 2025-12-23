import React, { useEffect, useState } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";
//import "../components/css/PrescriptionTabs.css"; // nowy plik CSS

export default function DoctorDataPatient() {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [patient, setPatient] = useState(location.state?.patientCard || null);  
  const [loading, setLoading] = useState(false);                                
  const [error, setError] = useState(null);

  // State dla recept
  const [prescriptions, setPrescriptions] = useState([]);
  const [loadingPrescriptions, setLoadingPrescriptions] = useState(false);
  const [prescriptionsError, setPrescriptionsError] = useState(null);
  const [expandedTab, setExpandedTab] = useState(null); // "list", "medicines", "create"

  // State dla rozwiniętej recepty
  const [expandedPrescription, setExpandedPrescription] = useState(null);

  // State dla formularza nowej recepty
  const [newPrescription, setNewPrescription] = useState({
    medicine: "",
    patientId: prescriptions.patientId || id,

    
   
  });
  const [creatingPrescription, setCreatingPrescription] = useState(false);

  // Pobranie danych pacjenta
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

  // Pobranie recept dla pacjenta
  const fetchPrescriptions = () => {
    if (!id) return;
    
    setLoadingPrescriptions(true);
    setPrescriptionsError(null);
    console.log("Fetching prescriptions for patient ID:", patientId);
    
    apiClient
      .get("/prescription/findByIdPatient", { params: { patientId } })
      .then((res) => {
        // Zakładamy, że backend zwraca listę PrescriptionDTO jako JSON
        setPrescriptions(res.data);
        console.log("Daty recept pobrane");
      })
      .catch((err) => {
        console.error("Błąd pobierania recept:", err);
        setPrescriptionsError("Nie udało się pobrać dat recept");
      })
      .finally(() => setLoadingPrescriptions(false));
  };

  const handleTabClick = (tab) => {
    if (expandedTab === tab) {
      setExpandedTab(null);
    } else {
      setExpandedTab(tab);
      if (tab === "list" || tab === "medicine") {
        fetchPrescriptions();
      }
    }
  };

  const handleCreatePrescription = async () => {
    if (!newPrescription.medicine) {
      alert("Wypełnij wymagane pole: leki");
      return;
    }

    setCreatingPrescription(true);
    try {
     
      const response = await apiClient.post("/prescription/create", {
        patientId: id,
        medicine: newPrescription.medicine,
        
      });

      alert("Recepta wystawiona pomyślnie!");
      setNewPrescription({
        medicine: "",
      });
      setExpandedTab(null);
      fetchPrescriptions();
    } catch (err) {
      console.error("Błąd podczas tworzenia recepty:", err);
      alert("Błąd podczas wystawienia recepty");
    } finally {
      setCreatingPrescription(false);
    }
  };

  const handleInputChange = (field, value) => {
    setNewPrescription(prev => ({
      ...prev,
      [field]: value
    }));
  };

  if (loading) {
    return <div className="patient-card-container"><div className="patient-card"><p>Ładowanie danych pacjenta...</p></div></div>;
  }

  if (error) {
    return <div className="patient-card-container"><div className="patient-card"><p style={{ color: "red" }}>Błąd: {String(error)}</p></div></div>;
  }

  if (!patient) {
    return <div className="patient-card-container"><div className="patient-card"><p>Brak danych pacjenta do wyświetlenia.</p></div></div>;
  }

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

        <hr />

        {/* Sekcja Recept */}
        <h3>Recepty</h3>
        <div className="prescription-tabs">
          {/* Tab 1: Lista recept */}
          <div className="prescription-tab">
            <button 
              className="tab-header"
              onClick={() => handleTabClick("list")}
            >
              <span>📋 Lista Recept Pacjenta</span>
              <span className="tab-arrow">{expandedTab === "list" ? "▼" : "▶"}</span>
            </button>
            {expandedTab === "list" && (
              <div className="tab-content">
                {loadingPrescriptions ? (
                  <p>Ładowanie recept...</p>
                ) : prescriptionsError ? (
                  <p style={{ color: "red" }}>{prescriptionsError}</p>
                ) : prescriptions.length > 0 ? (
                  <ul className="prescriptions-list">
                    {prescriptions.map((rx, idx) => (
                      <li key={rx.code || idx}>
                        <button 
                          className="prescription-date-btn"
                          onClick={() => setExpandedPrescription(expandedPrescription === rx.code ? null : rx.code)}
                        >
                          {rx.issueDate} {expandedPrescription === rx.code ? "▼" : "▶"}
                        </button>
                        {expandedPrescription === rx.code && (
                          <div className="prescription-details">
                            <p><strong>Kod recepty:</strong> {rx.code}</p>
                            <p><strong>Lekarz:</strong> {rx.firstNameDoctor} {rx.lastNameDoctor}</p>
                            <p><strong>Lek:</strong> {rx.medicine}</p>
                          </div>
                        )}
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>Brak recept dla tego pacjenta.</p>
                )}
              </div>
            )}
          </div>

          {/* Tab 2: Przepisane leki */}
          <div className="prescription-tab">
            <button 
              className="tab-header"
              onClick={() => handleTabClick("medicine")}
            >
              <span>💊 Przepisane leki</span>
              <span className="tab-arrow">{expandedTab === "medicine" ? "▼" : "▶"}</span>
            </button>
            {expandedTab === "medicine" && (
              <div className="tab-content">   
                <p><strong>Leki aktualnie stosowane:</strong></p>
                {prescriptions.length > 0 ? (
                  <ul className="medicines-list">
                    {prescriptions.map((rx, idx) => (
                      <li key={rx.code || idx}>{rx.medicine}</li>
                    ))}
                  </ul>
                ) : (
                  <p>Brak danych o lekach</p>
                )}
              </div>
            )}
          </div>

          {/* Tab 3: Wystawienie nowej recepty */}
          <div className="prescription-tab">
            <button 
              className="tab-header"
              onClick={() => handleTabClick("create")}
            >
              <span>✏️ Wystaw Receptę</span>
              <span className="tab-arrow">{expandedTab === "create" ? "▼" : "▶"}</span>
            </button>
            {expandedTab === "create" && (
              <div className="tab-content">
                <form className="prescription-form" onSubmit={(e) => { e.preventDefault(); handleCreatePrescription(); }}>
                  <div className="form-group">
                    <label>Lek (wymagane) *</label>
                    <input
                      type="text"
                      value={newPrescription.medicine}
                      onChange={(e) => handleInputChange("medicine", e.target.value)}
                      placeholder="Nazwa leku"
                      required
                    />
                  </div>

                  <button 
                    type="submit"
                    className="btn-create-prescription"
                    disabled={creatingPrescription}
                  >
                    {creatingPrescription ? "Tworzenie..." : "Wystawić Receptę"}
                  </button>
                </form>
              </div>
            )}
          </div>
        </div>

        <hr />

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