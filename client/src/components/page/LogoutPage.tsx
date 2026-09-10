import React from 'react'
import { useAuth } from '../../contexts/AuthContext'
import { useNavigate } from 'react-router-dom';

export default function LogoutPage() {
  const { logout } = useAuth();
  const navigate = useNavigate()

  function handleLogout() {
    logout();
    navigate('/');
  }

  function handleCancel() {
    navigate(-1);
  }

  return (
    <div className="d-flex align-items-center justify-content-center min-vh-100 px-3">
      <div
        className="card border-gold bg-dark bg-opacity-75 p-4 text-center shadow-lg font-serif"
        style={{ maxWidth: '420px', width: '100%' }}
      >
        <h2 className="text-gold text-uppercase fw-bold tracking-wider fs-4 mb-3">
          Confirm Logout
        </h2>

        <p className="text-parchment opacity-75 fs-7 mb-4">
          Your current game state will be saved.
        </p>

        <div className="d-flex gap-2 justify-content-center">
          <button
            type="button"
            onClick={handleCancel}
            className="btn btn-outline-secondary text-uppercase fw-bold fs-7 tracking-wider px-4 flex-fill"
          >
            Cancel
          </button>
          <button
            type="button"
            onClick={handleLogout}
            className="btn btn-danger text-uppercase fw-bold fs-7 tracking-wider px-4 flex-fill"
          >
            Log Out
          </button>
        </div>
      </div>
    </div>
  );
}
