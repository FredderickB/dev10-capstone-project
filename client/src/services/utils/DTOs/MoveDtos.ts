
export interface MoveRequestDto {
    gameId: number
    moveNumber: number
    playerSan: string
    engineLevel: number
    currentFen: string
    peakedAtBoard: boolean
}

export interface MoveResponseDto {
    gameId: number
    moveNumber: number
    updatedFen: string
    playerSan: string
    engineSan: string
    message: string
}

export interface Move {
    moveNumber: number
    moveSan: string
    fenAfter: string
}

export interface PgnTurn {
    turnNumber: number
    whiteMove?: string
    blackMove?: string
}