import React from "react";
import { Link } from "react-router-dom";
import "./css/Menu.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import TopBar from "./Topbar";


function Menu() {




  return (



    <div className="menu-container">

      <nav>
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
      </nav>
  
      
  </div>


  );
}

export default Menu;
