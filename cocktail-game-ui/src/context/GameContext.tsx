import { createContext, useContext, useState, ReactNode } from "react";
import {
  GuessLetterProps,
  startGame,
  guessLetter,
  skipRound,
  finishGame,
  revealLetter,
  showCocktailHintInfo,
} from "../service/ApiService";

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
  handleGuessLetter: (props: GuessLetterProps) => void;
  handleSkipRound: () => void;
  handleFinishGame: () => void;
  handleRevealLetter: () => void;
  handleShowCocktailHintInfo: (param: string) => void;
};

export const GameContext = createContext({} as GameContext);

const handleStartGame = async (name: string) => {
  const player = { playerName: name };
  const game = await startGame(player);

  console.log("Game started:", game);
};

const handleSkipRound = async (setGame: (game: Game) => void) => {
  const updatedGame = await skipRound();
  setGame(updatedGame);
  console.log("Guess cocktail:", updatedGame?.cocktail);
};

const handleFinishGame = async (setGame: (game: Game) => void) => {
  const game = await finishGame();
  setGame(game);
};

const handleRevealLetter = async (setGame: (game: Game) => void) => {
  const game = await revealLetter();
  setGame(game);
};

const handleGuessLetter = async (
  props: GuessLetterProps,
  setGame: (game: Game) => void
) => {
  const updatedGame = await guessLetter(props);
  setGame(updatedGame); // Update the game in the context after guessing a letter
  console.log("Guess cocktail:", updatedGame?.cocktail);
};

const handleShowCocktailHintInfo = async (
  param: string,
  setGame: (game: Game) => void
) => {
  const game = await showCocktailHintInfo(param);
  setGame(game);
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
        handleGuessLetter: (props: GuessLetterProps) =>
          handleGuessLetter(props, setGame),
        handleSkipRound: () => handleSkipRound(setGame),
        handleFinishGame: () => handleFinishGame(setGame),
        handleRevealLetter: () => handleRevealLetter(setGame),
        handleShowCocktailHintInfo: (param: string) =>
          handleShowCocktailHintInfo(param, setGame),
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
