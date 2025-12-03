// TopBar.jsx
import { Link } from "react-router-dom";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./css/Topbar.css";

 function TopBar() {

  const isLoggedIn = !!sessionStorage.getItem("token")

  const navigate = useNavigate();


  const handleLogout = () => {
    sessionStorage.removeItem("token");
    navigate("/");
  };

 
  return (

    <nav className="topbar">
      <button className="home-btn" onClick={() => navigate("/")}>Home</button> 
      <div className="navbarlogin-buttons">
        {isLoggedIn ? (
          <button className="logout-btn" onClick={handleLogout}>Logout</button>
        ) : (
          <>
          <button className="login-btn" onClick={() => navigate("/login")}>Login</button>
          <button className="register-btn" onClick={() => navigate("/registration")}>Register</button>
         </>
        )}
     
     </div>
    </nav>
  );
  
}


export default TopBar;
