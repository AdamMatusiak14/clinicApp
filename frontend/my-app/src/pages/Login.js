import { useState } from "react";
import "../components/css/Login.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", form);
      const data = response.data;
      console.log(data);

      sessionStorage.setItem("token", data.token);
      navigate("/");
    } catch (error) {
      alert("Błąd: " + (error.response?.status ?? error.message));
      navigate("/login");
    }
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>Logowanie:</h2>
        <input
          type="text"
          placeholder="Email"
          value={form.email}
          onChange={(e) => setForm({ ...form, email: e.target.value })}
          required
        />
        <input
          type="password"
          placeholder="Hasło"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
          required
        />
        <button type="submit">Zaloguj</button>
        {error && <p>{error}</p>}
      </form>
    </div>
  );
}

export default LoginForm;