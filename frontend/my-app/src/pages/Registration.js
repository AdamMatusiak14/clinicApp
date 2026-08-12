import React, { useState } from "react";
import '../components/css/Registration.css';
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Registration = () => {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

const navigate = useNavigate();
const [validationErrors, setValidationErrors] = useState({});

  const handleChange = (e) => {
    setValidationErrors(prevErrors => ({ ...prevErrors, [e.target.name]: "" })); // Clear validation error for the field

    setForm({ ...form, [e.target.name]: e.target.value });

  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/patient/register", form)  
    .then((response) => {
      alert("Rejestracja zakończona sukcesem: " + response.data);  
      navigate("/patient"); // Redirect to login page after successful registration
    })
    .catch((error) => {
      if(error.response && error.response.status === 400){
        setValidationErrors(error.response.data); // Set validation errors from the server response
        alert("Sprawdź pola formularza pod kątem błędów")

      } else if(error.response){
        alert("Bład serwera: "+ error.response.data);
        console.error("Błąd serwera:", error.response);
      }else{
        alert("Wystąpił nieoczekiwany bład. Spróbuj ponownie później");  
        console.error("Błąd:", error);
      }
  });
};

  return (
    <div className="registration-container">
    <form onSubmit={handleSubmit} className="registration-form">
      <div>
        <label>Imię:</label>
        <input
          type="text"
          name="firstName"
          value={form.firstName}
          onChange={handleChange}
          required
        />
        {validationErrors.firstName && <div className="error-message">{validationErrors.firstName}</div>}
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
        
        {validationErrors.lastName && <div className="error-message">{validationErrors.lastName}</div>}
      </div>
<div>
       <label>Email:</label>
        <input
          type="text"
          name="email"
          value={form.email}
          onChange={handleChange}
          required
       
        />
         {validationErrors.email && <div className="error-message">{validationErrors.email}</div>}
      </div>
      <div>
        <label>Hasło:</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
          required
        />
         {validationErrors.password && <div className="error-message">{validationErrors.password}</div>}
      </div>
      <button type="submit">Zarejestruj się</button>
    </form>
    </div>
  );
};

export default Registration;
    