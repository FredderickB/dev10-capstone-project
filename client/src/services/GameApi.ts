import type { Result } from "./utils/Result";
import { makeResult } from "./utils/Result";
import type { GameRequestDto, GameResponseDto } from "./utils/DTOs/GameDtos";

const API_URL = "http://localhost:8080/api/games";


export async function createGame(jwtToken: string | null, requestDto: GameRequestDto): Promise<Result<GameResponseDto>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'POST',
    headers: {
      'Authorization': jwtToken,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestDto),
  }

  const response = await fetch(`${API_URL}`, config)
  return await makeResult<GameResponseDto>(response);

}

export async function fetchGame(jwtToken: string | null, gameId: number): Promise<Result<GameResponseDto>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'GET',
    headers: {
      'Authorization': jwtToken,
      'Content-Type': 'application/json',
    },
  }

  const response = await fetch(`${API_URL}/${gameId}`, config)
  return await makeResult<GameResponseDto>(response);

}
