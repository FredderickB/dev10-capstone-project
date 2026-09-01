import React, { useState, useEffect } from 'react'
import type { Move, PgnTurn } from '../services/utils/DTOs/MoveDtos'
import { fetchGameMoves } from '../services/MoveApi';
import { useAuth } from '../contexts/AuthContext';
import { useParams } from 'react-router-dom';
import { formatMovesToPgn } from '../utils/MoveUtils';

interface props {
  moveNumber: number | undefined
}
export default function MatchMoveList({moveNumber}: props) {

  const { gameId: rawGameId } = useParams<{ gameId: string }>();
  const { token } = useAuth();
  const [moveList, setMoveList] = useState<Move[]>([])
  let pgnList: PgnTurn[] = formatMovesToPgn(moveList);

  useEffect(() => {
    async function loadMoveList() {
      if (!rawGameId) return;

      const parsedGameId = parseInt(rawGameId, 10);
      if (isNaN(parsedGameId)) return;
      const result = await fetchGameMoves(token, parsedGameId);

      if (result.success && result.data) {
        setMoveList(result.data)
      } else {
        console.log("failed to fetch move lsist")
      }
    }

    pgnList = formatMovesToPgn(moveList);
    console.log(pgnList)

    loadMoveList();
  }, [rawGameId, token, moveNumber]);



  return (
    <>
      <ul>
        {
          pgnList.map((move, index) => {
            return <li key={move.turnNumber}>
              {move.turnNumber}: {move.whiteMove} {move.blackMove}
            </li>
          })
        }

      </ul>

    </>
  )
}
