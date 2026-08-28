import React from 'react'
import { useAuth } from '../AuthContext'
import { useNavigate } from 'react-router-dom';

export default function LogoutPage() {
    const { token, setToken} = useAuth();
    const navigate = useNavigate()

    function handleClick() {
        setToken(null);
        navigate('/')
    }

  return (
    <>
        <h2>confirm logout</h2>
        <button onClick={handleClick}> log out </button>
    </>
  )
}
