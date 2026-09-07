import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';

import Board from '../midsize/Board';
import MatchInfo from '../midsize/MatchInfo';
import BoardActions from '../smallsize/BoardActions';
import PlayerActions from '../smallsize/PlayerActions';
import blindfoldImg from '../../assets/BlindfoldTerraria.webp';
import '../../styles/GamePage.css'

import { useAuth } from '../../contexts/AuthContext';
import MoveResponseContainer from '../midsize/MoveResponseContainer';
import { useGameSession } from '../../hooks/useGameSession';
import MatchMoveList from '../midsize/MatchMoveList';

export default function GamePage() {
  const { gameId: rawGameId } = useParams<{ gameId: string }>();
  const { token } = useAuth();
  const navigate = useNavigate()

  const {
    gameState,
    handleBoardToggle,
    handleInfoToggle,
    handleInputChange,
    handleMoveSubmission,
    handleResign
  } = useGameSession(rawGameId, token)

  useEffect(() => {
    if (!token) {
      navigate('/')
    }
  }, [token, navigate])

  return (
   <div className="container-fluid px-lg-4 py-2 vh-100 overflow-hidden d-flex flex-column">
      <div className="row g-2 justify-content-center align-items-stretch flex-grow-1 h-100 overflow-hidden">

        {/* LEFT COLUMN: Board & Primary Player Actions */}
        <div className="col-12 col-md-7 col-lg-6 d-flex flex-column gap-2 h-100 overflow-hidden">

          {/* Main Board Container (Board + Peek Toggle & Move Input) */}
          <div className="p-3 border border-gold rounded-3 bg-dark bg-opacity-75 shadow-lg d-flex flex-column align-items-center justify-content-between flex-grow-1 overflow-hidden min-h-0">

            {/* Square Fixed Board Area */}
            <div className="w-100 d-flex justify-content-center align-items-center flex-grow-1 min-h-0 overflow-hidden">
              <div className="dojo-board-frame w-100 d-flex justify-content-center align-items-center rounded bg-black bg-opacity-50 p-2 overflow-hidden">
                {gameState.viewBoard ? (
                  <Board fen={gameState.fen} playerColor={gameState.gameDto?.playerColor} />
                ) : (
                  <div className="text-center p-3">
                    <img
                      src={blindfoldImg}
                      alt="Blind Dojo Logo"
                      width="42"
                      height="42"
                      className="object-fit-contain mb-2"
                    />
                    <p className="text-parchment opacity-50 tracking-wider text-uppercase font-serif mb-0 fs-6">
                      [ Board Hidden ]
                    </p>
                  </div>
                )}
              </div>
            </div>

            {/* Bottom Actions: Toggle Peek & Enter Move */}
            <div className="w-100 mt-2 flex-shrink-0">
              <BoardActions
                boardToggle={handleBoardToggle}
                infoToggle={handleInfoToggle}
                handleInputChange={handleInputChange}
                handleMoveSubmission={handleMoveSubmission}
                playerSan={gameState.playSan}
                gameStatus={gameState.gameStatus}
              />
            </div>
          </div>

          {/* Bottom Left: Resign / Play Later Container */}
          <div className="p-2 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-50 shadow-sm flex-shrink-0">
            <PlayerActions handleResign={handleResign} gameStatus={gameState.gameStatus} />
          </div>
        </div>

        {/* RIGHT COLUMN: Unified Sidebar */}
        <div className="col-12 col-md-5 col-lg-5 col-xl-4 h-100 overflow-hidden">
          <div className="p-3 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-50 shadow-sm h-100 d-flex flex-column gap-2 overflow-hidden">

            {/*  Match Info Header */}
            <div className="p-2 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-75 flex-shrink-0">
              <h2 className="fs-6 text-uppercase text-gold tracking-wider mb-2 font-serif border-bottom border-secondary border-opacity-25 pb-1">
                Match Status
              </h2>
              {gameState.viewInfo ? (
                <MatchInfo
                  
                  moveNumber={gameState.moveNumber}
                  moveColor={gameState.colorToMove}
                />
              ) : (
                <p className="text-parchment opacity-50 fst-italic text-center my-1 fs-7">[ status hidden ]</p>
              )}
            </div>

            {/*  Move History List  */}
            <div 
              className="p-2 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-75 d-flex flex-column overflow-hidden min-h-0 flex-shrink-0"
              style={{ height: '60%' }}
            >
              <h2 className="fs-6 text-uppercase text-gold tracking-wider mb-2 font-serif border-bottom border-secondary border-opacity-25 pb-1 flex-shrink-0">
                Move History
              </h2>
              <div className="flex-grow-1 overflow-auto pe-1 min-h-0">
                {gameState.viewInfo ? (
                  <MatchMoveList moveNumber={gameState.moveNumber} />
                ) : (
                  <p className="text-parchment opacity-50 fst-italic text-center my-2 fs-7">[ history hidden ]</p>
                )}
              </div>
            </div>

            {/*  Engine Response & Error Container  */}
            <div className="p-2 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-75 flex-grow-1 d-flex flex-column overflow-hidden min-h-0">
              <h2 className="fs-6 text-uppercase text-gold tracking-wider mb-2 font-serif border-bottom border-secondary border-opacity-25 pb-1 flex-shrink-0">
                Engine Response [{gameState.gameDto?.engineLevel}] ELO
              </h2>
              <div className="flex-grow-1 overflow-auto pe-1 min-h-0">
                <MoveResponseContainer
                  errors={gameState.errors}
                  engineResponse={gameState.engineSan}
                  gameStatus={gameState.gameStatus}
                />
              </div>
            </div>

          </div>
        </div>

      </div>
    </div>
  );
}
