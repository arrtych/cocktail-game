import { createContext, useContext, useState, ReactNode } from "react";
import { startGame } from "../service/ApiService";

interface Player {
  id: number;
  name: string;
  score: number;
}
interface Game {
  id: number;
}
type GameContext = {
  game: any;
  player: Player | null;
  setPlayer: (player: Player) => void;
  setGame: (game: Game) => void;
  handleStartGame: (name: string) => void;
};

export const GameContext = createContext({} as GameContext);

const handleStartGame = async (name: string) => {
  const player = { playerName: name };
  const game = await startGame(player);

  console.log("Game started:", game);
};

export const GameContextProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [player, setPlayer] = useState<Player | null>(null);
  const [game, setGame] = useState<Game | null>(null);
  return (
    <GameContext.Provider
      value={{
        game,
        handleStartGame,
        player,
        setPlayer,
        setGame,
      }}
    >
      {children}
    </GameContext.Provider>
  );
};

/**
 *
 * @returns
 */
export const useGameContext = () => {
  const context = useContext(GameContext);
  if (!context) {
    throw new Error("useGameContext must be used within a GameContextProvider");
  }
  return context;
};
