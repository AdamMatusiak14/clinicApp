import { useState } from "react";
import "../components/css/Assistant.css";
import axios from "axios";

 function Assistant() {
  const [input, setInput] = useState("");
  const [response, setResponse] = useState("");

  const sendDescription = async () => {
    try {
      const res = await axios.post("http://localhost:8080/assistant/description", {description: input});
      setResponse(res.data || "Brak odpowiedzi");
    } catch (error) {
      console.error("Błąd podczas wysyłania opisu:", error);
    }
  }

 
  return (
    <div className="assistant-container">
      <h3>Przykładowy opis:</h3>
      <p>„Od kilku dni mam wysoką gorączkę i zaczyna mnie to martwić. Nie wiem, co jest tego przyczyną. Mam też czerwone plamy na rękach i nogach, niektóre z nich są opuchnięte”.</p>

      <p>Odpowiedź na opis: Ospa wietrzna</p>


      <h3>Twój opis:</h3>
      <textarea
        value={input}
        onChange={(e) => setInput(e.target.value)}
        rows="4"
        cols="50"
        placeholder="Wpisz tutaj swoje dolegliwości..."
      />

      <br />
      <button type="button" className="assistant-form-button" onClick={sendDescription}>Wyślij</button>

      <h3>Odpowiedź:</h3>
      <div className="assistant-response">{response}</div>
    </div>
  );
}

export default Assistant;
