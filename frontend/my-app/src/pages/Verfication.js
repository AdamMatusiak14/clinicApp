import React, { useState } from "react";
import "../components/css/DataPatient.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../components/css/Verfication.css";

const Verfication = () => {
  const [form, setForm] = useState({
   firstName: "",
   lastName: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

     console.log(form.firstName);
      console.log(form.lastName);

    axios
      .post("http://localhost:8080/patient/verify", form)
      .then((response) => {
        const patient = response.data;
        localStorage.setItem("patientId", patient.id);
        navigate("/survey");
        
     
      }).catch((error) => {
          alert("Błąd: " + error.response?.status);
          navigate("/patient");
});

   
};

  
  return (
   <div className="verfication-container">
      <form className="verfication-form" onSubmit={handleSubmit} >
        <div>
          <label>Imię:</label>
          <input
            type="text"
            name="firstName"
            value={form.firstName}
            onChange={handleChange}
            required
     />
     </div>

      <div>
          <label>Nazwisko:</label>
          <input
            type="text"
            name="lastName"
            value={form.lastName}
            onChange={handleChange}
            required
     />
        </div>
        <button type="submit">Wyślij</button>
      </form>
    </div>
  );
};

export default Verfication;  