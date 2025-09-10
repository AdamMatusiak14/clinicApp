import React,  { useState, useEffect } from "react";
import axios from "axios";
import "../components/css/Visitis.css";


 function Visits() {

   const [form, setForm] = useState({ 
    firstName: "", 
    lastName: "", 
    doctor: "", 
    date: "", 
    time: "" 
  });

  const [doctors, setDoctors] = useState([]);
  const [times, setTimes] = useState([]);

  
  useEffect(() => {
      axios.get("http://localhost:8080/api/doctors/names")
      .then((response) => {
        console.log("To przychodzi z backendu", response.data); // sprawdź co zwraca backend
        setDoctors(response.data);  // ustaw dane w stanie
      })
      .catch((error) => {
        console.error("Błąd podczas pobierania lekarzy:", error);
      });
  }, []);


 useEffect(() => {
  if (!form.date || !form.doctor) return;

  axios.get(`http://localhost:8080/api/visit/available?doctorId=${form.doctor}&date=${form.date}`)
       .then(res => setTimes(res.data))
       .catch(err => console.error(err));
}, [form.date, form.doctor]);



  const handleSubmit = async (e) => {
  e.preventDefault();
  const payload = {
    doctor: form.doctor,
    firstName: form.firstName,
    lastName: form.lastName,
    date: form.date,
    time: form.time
  };

    console.log("Payload do wysłania:", payload);


try {
    await axios.post("http://localhost:8080/api/visit/create", payload);
    alert("Wizyta została umówiona!");
    // czyścimy formularz
    setForm({
      firstName: "",
      lastName: "",
      doctor: "",
      date: "",
      time: ""
    });
    setTimes([]); // też można wyczyścić dostępne godziny
  } catch (error) {
    console.error("Błąd podczas umawiania wizyty:", error);
    alert("Wystąpił błąd podczas umawiania wizyty.");
  }
};


  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });
 


  return (
    <div className="visits-container">
      <form className="visits-form" onSubmit={handleSubmit}>

        <label htmlFor="firstName">Imię</label>
        <input type="text" id="firstName" name="firstName" placeholder="Wpisz imię" required value={form.firstName}
          onChange={handleChange} />

        <label htmlFor="lastName">Nazwisko</label>
        <input type="text" id="lastName" name="lastName" placeholder="Wpisz nazwisko"  required value={form.lastName}
          onChange={handleChange}/>
        
       <label htmlFor="date">Data wizyty</label>
        <input type="date" id="date" name="date"  min={new Date().toISOString().split("T")[0]} onChange={handleChange}
       value={form.date} />

         <label htmlFor="doctor">Lekarz</label>
        <select id="doctor" name="doctor" required value={form.doctor} onChange={handleChange} >
          <option value="">Wybierz lekarza</option>
          {doctors.map(doc => (
            <option key={doc.id} value={doc.id}>
              {doc.firstName} {doc.lastName}
            </option>
          ))}
        </select>

         <label htmlFor="time">Godzina wizyty</label>
        <select id="time" name="time" required value={form.time} onChange={handleChange}> 
          <option value="">Wybierz godzinę</option>
         {times.map(t => <option key={t} value={t}>{t}</option>)}
        </select> 

       
    
          
    
      
      <button type="submit" className="submit-button">Umów wizytę</button>
    </form>
    </div>
  );
} 

 export default Visits;
