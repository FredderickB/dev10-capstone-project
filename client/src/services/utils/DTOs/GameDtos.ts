
export interface GameRequestDto {

    playerColor: string;
    engineLevel: number;
    
}

export interface GameResponseDto {

    gameId : number;
    fen : string;
    playerColor: string;
    status : string;
    engineLevel : number;

}