import type { MoveResponseDto, MoveRequestDto, Move } from "./utils/DTOs/MoveDtos";
import type { Result } from "./utils/Result";
import { makeResult } from "./utils/Result";


const API_URL = "http://localhost:8080/api/move";

export async function sendMove(jwtToken: string | null, moveRequest: MoveRequestDto): Promise<Result<MoveResponseDto>> {


  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(moveRequest),
  }

  const response = await fetch(`${API_URL}`, config)
  return await makeResult<MoveResponseDto>(response);

}

export async function fetchGameMoves(jwtToken: string | null, gameId: number): Promise<Result<Move[]>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
    },
  }

  const response = await fetch(`${API_URL}/${gameId}`, config)
  return await makeResult<Move[]>(response);

}