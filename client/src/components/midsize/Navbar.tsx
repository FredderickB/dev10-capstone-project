import React from 'react'
import { NavLink } from 'react-router-dom'
import { useAuth } from '../../contexts/AuthContext';
import { useState } from 'react';
import blindfoldImg from '../../assets/BlindfoldTerraria.webp'
import '../../styles/Navbar.css'

interface props {
    closeMenu: () => void
}

const AuthLinks = ({closeMenu}: props) => (
    <>
        <li className="nav-item">
            <NavLink to="/profile" className="nav-link dojo-link border border-gold rounded-3" onClick={closeMenu}>
                Profile
            </NavLink>
        </li>
        <li className="nav-item">
            <NavLink to="/logout" className="nav-link dojo-link border border-gold rounded-3">
                Log Out
            </NavLink>
        </li>
    </>
);

export default function Navbar() {


    const { token } = useAuth();
    const [isOpen, setIsOpen] = useState(false);

    const toggleMenu = () => setIsOpen((prev) => !prev);
    const closeMenu = () => setIsOpen(false);

    return (
       <nav className="navbar navbar-expand-lg border-bottom border-secondary border-opacity-25 px-4 py-3 dojo-navbar">
      <div className="container-fluid">
        <NavLink 
          to="/" 
          className="navbar-brand d-flex align-items-center gap-2 text-decoration-none" 
          onClick={closeMenu}
        >
            <div className="d-flex align-items-center justify-content-center p-1">
            <img 
              src={blindfoldImg} 
              alt="Blind Dojo Logo" 
              width="36" 
              height="36" 
              className="object-fit-contain"
            />
          </div>
          <span className="fw-semibold tracking-wider text-parchment fs-4 text-uppercase">
            Blind Chess
          </span>
        </NavLink>

        <button
          className="navbar-toggler border-0 shadow-none text-parchment"
          type="button"
          onClick={toggleMenu}
          aria-expanded={isOpen}
          aria-label="Toggle navigation"
        >
          <span className="fs-3">☰</span>
        </button>

        <div className={`collapse navbar-collapse ${isOpen ? 'show' : ''}`}>
          <ul className="navbar-nav ms-auto gap-lg-4 text-end text-lg-start mt-3 mt-lg-0">
            {token && (
              <AuthLinks closeMenu={closeMenu} />
            ) }
          </ul>
        </div>
      </div>
    </nav>
  );

}
