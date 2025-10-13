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
  
       <table>
          <thead>
            <tr>
              <th>Imię</th>
              <th>Nazwisko</th>
              <th>Hasło</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Robute</td>
              <td>Guiliman</td>
              <td>robu</td>
             </tr>
          </tbody>
          <tbody>
            <tr>
              <td>Leman</td>
              <td>Russ</td>
              <td>lema</td>
             </tr>
          </tbody>
        </table>
</div>

  );
}

export default Menu;
