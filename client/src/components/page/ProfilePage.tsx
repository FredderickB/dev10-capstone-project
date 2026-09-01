import React from 'react'
import UserStats from '../UserStats'
import GamesTable from '../GamesTable'
import { useAuth } from '../../contexts/AuthContext'
import { useProfileState } from '../../hooks/useProfileState'

export default function ProfilePage() {

  const { token } = useAuth();
  const { profileState } = useProfileState(token);


  return (
    <>
    <h3>Hello user</h3>
      <GamesTable games={profileState.games}/>
    </>
  )
}
