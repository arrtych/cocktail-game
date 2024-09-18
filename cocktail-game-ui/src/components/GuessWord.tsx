import { Box } from "@mui/material";
import React from "react";
import { useGameContext } from "../context/GameContext";

export interface GuessWordProps {
  word?: string; //todo
}

const wordLength = 9;
const boxStyle = {
  display: "flex",
  justifyContent: "center",
  fontSize: "2em",
  fontWeight: "700",
  gap: "1em",
  marginTop: "2.5em",
};

const letterStyle = {};
const GuessWord: React.FC<GuessWordProps> = (props: GuessWordProps) => {
  const { game } = useGameContext();
  return (
    <Box sx={boxStyle} className="guess-word">
      {game?.playerGuess?.map((letter: string, index: number) => (
        <Box key={index} sx={letterStyle}>
          {letter.toUpperCase()}
        </Box>
      ))}
    </Box>
  );
};

export default GuessWord;
