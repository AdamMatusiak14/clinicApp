import React from "react";
import { Link, Outlet } from "react-router-dom";
import '../components/css/PatientMenu.css'; // Import your CSS file for styling

function PatientMenu() {
  return (
    <div>
      <h2 className="patient-menu-title">Menu Pacjenta</h2>
      <nav>
        <ul className="patient-menu-list">
          <li><Link className= "patient-menu-link" to="/registration">Rejestracja</Link></li>
          <li><Link className= "patient-menu-link" to="/login">Ankieta</Link></li>
          <li><Link className= "patient-menu-link" to="/patientCard">Karta Pacjenta</Link></li>
          <li><Link className= "patient-menu-link" to="/visits">Wizyty</Link></li>
        </ul>
      </nav>
      <Outlet /> {/* Tu będą wyświetlane podstrony */}
    </div>
  );
}

export default PatientMenu;