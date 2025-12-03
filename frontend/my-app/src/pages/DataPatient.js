import React, { useState, useEffect } from "react";
import "../components/css/DataPatient.css";
import { useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";

const DataPatient = () => {
  const [form, setForm] = useState({
    age: "",
    sex: "",
    takingMedication: "",
    pastIllnesses: "",
    chronicDiseases: "",
    vaccinations: "",
    allergies: "",
    familyHistory: "",
    smoking: "",
    alcohol: "",
   });

  const [patientCard, setPatientCard] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    apiClient
      .get("/patient/current")
      .then((res) => {
               setPatientCard(res.data);
      
      })
      .catch((err) => {
        console.error("Error fetching current patient:", err);
        if (err?.response?.status === 401) navigate("/login");
      })
      .finally(() => setLoading(false));
  }, [navigate]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

   

      apiClient
      .put("/patient/survey/note", form)
      .then((response) => {
        alert("Ankieta zostala zapisana: " + response.data);
        navigate("/patient");
      })
      .catch((error) => {
        alert("Błąd zapisu ankiety: " + (error.message || error));
      });
  };

  if (loading) return <div className="datapatient-container">Ładowanie danych pacjenta...</div>;
  if (!patientCard) return <div className="datapatient-container">Nie zweryfikowano pacjenta.</div>;

  return (
    <div className="datapatient-container">
      <div className="patient-verified">
   
         <strong className="patient-label">Zalogowany pacjent:</strong>
+        <span className="patient-name">{patientCard.name} {patientCard.surname}</span>
      </div>

      <form onSubmit={handleSubmit} className="datapatient-form">
        {/* ukryte id pacjenta (ustawione z /patient/current) */}
   

        <div>
          <label>Wiek:</label>
          <input
            type="number"
            name="age"
            value={form.age}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Płeć:</label>
          <input
            type="text"
            name="sex"
            value={form.sex}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Przyjmowane leki:</label>
          <input
            type="text"
            name="takingMedication"
            value={form.takingMedication}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Przebyte choroby:</label>
          <input
            type="text"
            name="pastIllnesses"
            value={form.pastIllnesses}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Choroby przewlekłe:</label>
          <input
            type="text"
            name="chronicDiseases"
            value={form.chronicDiseases}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Szczepienia:</label>
          <input
            type="text"
            name="vaccinations"
            value={form.vaccinations}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Alergie:</label>
          <input
            type="text"
            name="allergies"
            value={form.allergies}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Historia rodzinna:</label>
          <input
            type="text"
            name="familyHistory"
            value={form.familyHistory}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Palenie:</label>
          <input
            type="text"
            name="smoking"
            value={form.smoking}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Alkohol:</label>
          <input
            type="text"
            name="alcohol"
            value={form.alcohol}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Wyślij</button>
      </form>
    </div>
  );
};

export default DataPatient;