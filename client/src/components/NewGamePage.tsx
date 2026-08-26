import React from 'react'
import EngineSelector from './EngineSelector'
import { Link } from 'react-router-dom'

export default function NewGamePage() {
  return (
    <div>
        <EngineSelector />
        <Link to='/game'> start match </Link>
        </div>
  )
}
