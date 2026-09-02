import React from 'react'
import UserStats from '../UserStats'
import GamesTable from '../GamesTable'
import { useAuth } from '../../contexts/AuthContext'
import { useProfileState } from '../../hooks/useProfileState'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

export default function ProfilePage() {

  const { token } = useAuth();
  const { profileState, ConfirmDeleteClick } = useProfileState(token);
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      navigate('/')
    }
  }, [token, navigate])

  return (
    <>
    <h3>Hello user</h3>
      <GamesTable games={profileState.games} ConfirmDeleteClick={ConfirmDeleteClick}/>
    </>
  )
}
