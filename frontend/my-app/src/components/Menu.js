import React from "react";
import { Link } from "react-router-dom";
import "./css/Menu.css";

function Menu() {
  return (
    <div className="menu-container">
      <ul className="menu-list">
        <li className="menu-item">
          <Link className="menu-link" to="/patient">Pacjenci</Link>
        </li>
        <li className="menu-item">
          <Link className="menu-link" to="/doctor">Lekarze</Link>
        </li>
        <li className="menu-item">
          <Link className="menu-link" to="/assistent">Asystent AI</Link>
        </li>
      </ul>
    </div>
  );
}

export default Menu;
