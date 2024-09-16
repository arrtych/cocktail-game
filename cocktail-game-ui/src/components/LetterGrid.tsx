import { Box } from "@mui/material";
import React from "react";
import { useGameContext } from "../context/GameContext";
import CustomButton from "./CustomButton";

const LetterGrid: React.FC = () => {
  const letters = "abcdefghijklmnopqrstvwxyz";
  const { game, handleGuessLetter, setGame } = useGameContext();

  const handleLetterClick = async (letter: string) => {
    try {
      console.log("letter", letter);
      const playerId = game?.player?.id;

      await handleGuessLetter({ playerId, letter });
      // setGame()
    } catch (error) {
      console.error("Failed to handleLetterClick:", error);
    }
  };

  const letterGridStyle = {
    display: "flex",
    justifyContent: "center",
    flexWrap: "wrap",
    marginTop: "2.5em",
  };

  const letterStyle = {
    display: "inline-block",
    // margin: "10px",
    border: "1px solid black",
    flex: "1 1 calc(10% - 10px)",
    boxSizing: "border-box",
    textAlign: "center",
    fontSize: "2em",
    fontWeight: "bold",
  };

  return (
    <Box sx={letterGridStyle}>
      {letters.split("").map((letter, index) => (
        <CustomButton
          key={index}
          variant="text"
          onClick={() => handleLetterClick(letter)}
        >
          <Box sx={letterStyle}>{letter.toUpperCase()}</Box>
        </CustomButton>
      ))}
    </Box>
  );
};

export default LetterGrid;
