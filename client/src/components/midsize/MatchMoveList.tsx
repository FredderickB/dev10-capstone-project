import React, { useState, useEffect, useRef } from 'react'
import type { Move, PgnTurn } from '../../services/utils/DTOs/MoveDtos'
import { fetchGameMoves } from '../../services/MoveApi';
import { useAuth } from '../../contexts/AuthContext';
import { useParams } from 'react-router-dom';
import { formatMovesToPgn } from '../../utils/MoveUtils';

interface props {
  moveNumber: number | undefined
}
export default function MatchMoveList({moveNumber}: props) {

  const { gameId: rawGameId } = useParams<{ gameId: string }>();
  const { token } = useAuth();
  const [moveList, setMoveList] = useState<Move[]>([])
  let pgnList: PgnTurn[] = formatMovesToPgn(moveList);
  const scrollRef = useRef<HTMLDivElement>(null);

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

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [pgnList.length]);


  return (
   <div ref={scrollRef} className="dojo-history-scroll h-100 overflow-auto pe-2">
      {pgnList.length === 0 ? (
        <p className="text-parchment opacity-50 fst-italic text-center my-3 fs-7">
          No moves played yet.
        </p>
      ) : (
        <div className="d-flex flex-column gap-1 font-serif fs-6">
          {pgnList.map((turn) => (
            <div 
              key={turn.turnNumber} 
              className="d-flex align-items-center py-1 px-2 rounded bg-black bg-opacity-25 border-bottom border-secondary border-opacity-10"
            >
              {/* Turn Number */}
              <span className="text-gold fw-bold opacity-75" style={{ width: '40px' }}>
                {turn.turnNumber}.
              </span>
              
              {/* White Move */}
              <span className="text-parchment fw-semibold flex-fill">
                {turn.whiteMove}
              </span>

              {/* Black Move */}
              <span className="text-parchment fw-semibold flex-fill">
                {turn.blackMove || ''}
              </span>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
