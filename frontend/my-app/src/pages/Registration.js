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

  const handleChange = (e) => {
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
        alert("Błąd rejestracji");    
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
<div>
       <label>Email:</label>
        <input
          type="text"
          name="email"
          value={form.email}
          onChange={handleChange}
          required
        />
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
      </div>
      <button type="submit">Zarejestruj się</button>
    </form>
    </div>
  );
};

export default Registration;
    