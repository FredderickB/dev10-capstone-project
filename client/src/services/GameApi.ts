import type { Result } from "./utils/Result";
import { makeResult } from "./utils/Result";
import type { GameRequestDto, GameResponseDto, GameSummaryDto } from "./utils/DTOs/GameDtos";

const API_URL = "http://localhost:8080/api/games";


export async function createGame(jwtToken: string | null, requestDto: GameRequestDto): Promise<Result<GameResponseDto>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestDto),
  }

  const response = await fetch(`${API_URL}`, config)
  return await makeResult<GameResponseDto>(response);

}

export async function deleteGame(jwtToken: string | null, gameId: number): Promise<Result<void>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }

  const response = await fetch(`${API_URL}/${gameId}`, config)
  return await makeResult<void>(response);

}

export async function fetchGame(jwtToken: string | null, gameId: number): Promise<Result<GameResponseDto>> {

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
  return await makeResult<GameResponseDto>(response);

}

export async function fetchGames(jwtToken: string | null): Promise<Result<GameSummaryDto[]>> {

  if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      "Content-Type": "application/json"
    },
  }

  const response = await fetch(`${API_URL}`, config)
  return await makeResult<GameSummaryDto[]>(response);

}

export async function sendResignation(jwtToken: string | null, gameId: number) :Promise<Result<void>>{

   if (!jwtToken) {
    jwtToken = ''
  }

  const config = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }

  const response = await fetch(`${API_URL}/${gameId}/resign`, config)
  return await makeResult<void>(response);

}
