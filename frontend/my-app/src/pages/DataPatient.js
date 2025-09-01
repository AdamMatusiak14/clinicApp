import React, { useState } from "react";
import "../components/css/DataPatient.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

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
    patient: {id:localStorage.getItem("patientId")},
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    axios
      .post("http://localhost:8080/patient/survey/note", form)
      .then((response) => {
        alert("Ankieta zostala zapisana: " + response.data);
        navigate("/patient");
      })
      .catch((error) => {
        alert("Błąd zapisu ankiety: " + error.message);
      });
  };

  return (
    <div className="datapatient-container">
      <form onSubmit={handleSubmit} className="datapatient-form">
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