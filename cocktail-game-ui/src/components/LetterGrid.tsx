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

  const letterDisabledStyle = {
    color: "black",
    backgroundColor: "#80808091",
  };

  return (
    <Box sx={letterGridStyle} className="letter-grid">
      {letters.split("").map((letter, index) => {
        const isLetterSelected = game?.selectedLetters.indexOf(letter) > -1;
        return (
          <CustomButton
            key={index}
            variant="text"
            onClick={() =>
              game && !isLetterSelected && handleLetterClick(letter)
            }
          >
            <Box
              sx={{
                ...letterStyle,
                ...((!game && letterDisabledStyle) ||
                  (game && isLetterSelected && letterDisabledStyle)),
              }}
            >
              {letter.toUpperCase()}
            </Box>
          </CustomButton>
        );
      })}
    </Box>
  );
};

export default LetterGrid;
