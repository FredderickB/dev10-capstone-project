import React from 'react'
import GamesTable from '../midsize/GamesTable'
import { useAuth } from '../../contexts/AuthContext'
import { useProfileState } from '../../hooks/useProfileState'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

export default function ProfilePage() {

  const { token, isRegisteredUser } = useAuth();
  const { profileState, ConfirmDeleteClick } = useProfileState(token);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isRegisteredUser) {
      navigate('/')
    }
  }, [token, navigate])

  return (
    <div className="container py-5 font-serif min-vh-100">
      <div className="d-flex align-items-center justify-content-between mb-4 border-bottom border-gold pb-3">
        <div>
          <h2 className="text-gold text-uppercase fw-bold tracking-wider fs-3 mb-1">
            {profileState.userName?.username ? `${profileState.userName.username}'s Profile` : 'Player Profile'}
          </h2>
        </div>
      </div>

      <div className="row">
        <div className="col-12">
          <GamesTable 
            games={profileState.games} 
            ConfirmDeleteClick={ConfirmDeleteClick} 
          />
        </div>
      </div>
    </div>
  )
}
