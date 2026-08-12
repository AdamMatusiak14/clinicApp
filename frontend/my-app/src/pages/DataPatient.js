import React, { useState, useEffect } from "react";
import "../components/css/DataPatient.css";
import { useNavigate } from "react-router-dom";
import apiClient from "../components/apiClient";

const DataPatient = () => {
  const [form, setForm] = useState({
    age: "",
    sex: "",
    takingMedication: "none detected",
    pastIllnesses: "none detected",
    chronicDiseases: "none detected",
    vaccinations: "none detected",
    allergies: "none detected",
    familyHistory: "none detected",
    smoking: "",
    alcohol: "",
   });

  const [patientCard, setPatientCard] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [errors, setErrors] = useState({});

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

    setErrors((prevErrors) => ({
      ...prevErrors,
      [e.target.name]: undefined,
    }));  
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({}); // Reset errors before submission

   

      apiClient
      .put("/patient/survey/note", form)
      .then((response) => {
        alert("Ankieta zostala zapisana: " + response.data);
        navigate("/patient");
      })
      .catch((error) => {
        console.error("Błąd zapisu ankiety: ", error);
        if(error.response && error.response.status === 400 && error.response.data) {
      setErrors(error.response.data)
      alert("Wystąpiły błędy walidacji. Sprawdź formularz");
    }else{
      alert("Błąd zapisu ankiety: " + (error.response?.data.message || error.message || error));
    }
  });
  };

  if (loading) return <div className="datapatient-container">Ładowanie danych pacjenta...</div>;
  if (!patientCard) return <div className="datapatient-container">Nie zweryfikowano pacjenta.</div>;

  return (
    <div className="datapatient-container">
      <div className="patient-verified">
   
         <strong className="patient-label">Zalogowany pacjent:</strong>
       <span className="patient-name">{patientCard.name} {patientCard.surname}</span>
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
          {errors.age && <span className="error-message">{errors.age}</span>}
        </div>
        <div>
          <label>Płeć:</label> 

    <select
            name = "sex"
            value = {form.sex}
            onChange = {handleChange}
            required
            >
              <option value="">Wybierz płeć</option>
              <option value="Male">Mężczyzna</option>
              <option value="Female">Kobieta</option>
              <option value="Other">Inna</option>
            </select>
            {errors.sex && <span className="error-message">{errors.sex}</span>}
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
        {errors.takingMedication && <span className="error-message">{errors.takingMedication}</span>}
        <div>
          <label>Przebyte choroby:</label>
          <input
            type="text"
            name="pastIllnesses"
            value={form.pastIllnesses}
            onChange={handleChange}
          />
        </div>
        {errors.pastIllnesses && <span className="error-message">{errors.pastIllnesses}</span>}
        <div>
          <label>Choroby przewlekłe:</label>
          <input
            type="text"
            name="chronicDiseases"
            value={form.chronicDiseases}
            onChange={handleChange}
          />
        </div>
         {errors.chronicDiseases && <span className="error-message">{errors.chronicDiseases}</span>}
        <div>
          <label>Szczepienia:</label>
          <input
            type="text"
            name="vaccinations"
            value={form.vaccinations}
            onChange={handleChange}
          />
        </div>
        {errors.vaccinations && <span className="error-message">{errors.vaccinations}</span>}
        <div>
          <label>Alergie:</label>
          <input
            type="text"
            name="allergies"
            value={form.allergies}
            onChange={handleChange}
          />
        </div>
         {errors.allergies && <span className="error-message">{errors.allergies}</span>}
        <div>
          <label>Historia rodzinna:</label>
          <input
            type="text"
            name="familyHistory"
            value={form.familyHistory}
            onChange={handleChange}
          />
        </div>
         {errors.familyHistory && <span className="error-message">{errors.familyHistory}</span>}
        <div>
          <label>Palenie:</label>
          <select
            name="smoking"
            value={form.smoking}
            onChange={handleChange}
            required
          >
              <option value ="">Wybierz opcję</option>
              <option value="Yes"> Tak </option>
              <option value ="No"> Nie </option>
            </select>
             {errors.smoking && <span className="error-message">{errors.smoking}</span>}
        </div>
        <div>
          <label>Alkohol:</label>
          <select
            name="alcohol"
            value={form.alcohol}
            onChange={handleChange}
            required
          >
            <option value="">Wybierz opcję</option>
            <option value = "Yes"> Tak </option>
            <option value = "No"> Nie </option>
          </select>
          {errors.alcohol && <span className="error-message">{errors.alcohol}</span>}
            </div>
        <button type="submit">Wyślij</button>
      </form>
    </div>
  );
};

export default DataPatient;