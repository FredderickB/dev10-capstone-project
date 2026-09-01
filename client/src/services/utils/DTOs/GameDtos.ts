
export interface GameRequestDto {

    gameId: number;
    playerColor: string;
    engineLevel: number;

}

export interface GameResponseDto {

    gameId: number;
    fen: string;
    playerColor: string;
    status: string;
    engineLevel: number;

}

export interface GameSummaryDto {
    gameId: number;
    engineLevel: number;
    boardPeaks: number;
    status: string;
    playerColor: string;
    totalMoves: number;
    createdAt: string;
    
}