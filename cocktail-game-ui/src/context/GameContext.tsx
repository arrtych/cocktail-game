import { createContext, useContext, useState, ReactNode } from "react";
import { startGame } from "../service/ApiService";

type GameContext = {
  game: any;
  handleStartGame: () => void;
};

export const GameContext = createContext({} as GameContext);

const handleStartGame = async () => {
  const player = { id: 1, name: "Alice2" }; // No score provided
  const game = await startGame(player);
  console.log("Game started:", game);
};

export const GameContextProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  //   const [words, setWords] = useState([]);
  //   const [] = useState<string[]>([]);
  //   const [game, setGame] = useState<any[]>([]);
  const [game, setGame] = useState([]);
  return (
    <GameContext.Provider
      value={{
        game,
        handleStartGame,
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
