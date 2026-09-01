import React, { useState } from 'react'
import EngineSelector from '../EngineSelector'
import { useNavigate } from 'react-router-dom'
import { createGame } from '../../services/GameApi'
import ColorSelector from '../ColorSelector'
import type { GameRequestDto } from '../../services/utils/DTOs/GameDtos'
import { useAuth } from '../../contexts/AuthContext'

export default function NewGamePage() {

  const [ request, setRequest ] = useState<GameRequestDto>({
    gameId: 0,
    playerColor : "WHITE",
    engineLevel: 1000,
  })
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate()
  const { token } = useAuth()
  const [errors, setErrors] = useState<string[] | null>(null);

  const handleColorChange = (color: string) => {
    setRequest((prev) => ({ ...prev, playerColor: color }));
  };

  const handleEngineChange = (level: number) => {
    setRequest((prev) => ({ ...prev, engineLevel: level }));
  };

  async function handleClick() {

    const result = await createGame(token, request);

    if(!result.success) {
      setErrors(result.errors)
    } else {

      const gameId = result.data?.gameId
      navigate(`/game/${gameId}`)
    }

  }

  return (
    <div>
      <EngineSelector value={request.engineLevel} onChange={handleEngineChange}/>
      <ColorSelector value={request.playerColor} onChange={handleColorChange}/>
      <div>
        <button onClick={handleClick}> start match </button>
      </div>
    </div>
  )
}
