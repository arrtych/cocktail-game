import { Box } from "@mui/material";
import React from "react";
import CustomButton from "./CustomButton";
import { useGameContext } from "../context/GameContext";

const HelpButtons: React.FC = () => {
  const { handleSkipRound, handleFinishGame, handleRevealLetter } =
    useGameContext();

  const skipRound = async () => {
    try {
      await handleSkipRound();
    } catch (error) {
      console.error("Failed to skipRound:", error);
    }
  };

  const finishGame = async () => {
    try {
      await handleFinishGame();
    } catch (error) {
      console.error("Failed to finishGame:", error);
    }
  };

  const revealLetter = async () => {
    try {
      await handleRevealLetter();
    } catch (error) {
      console.error("Failed to revealLetter:", error);
    }
  };

  const showCocktailInfo = async () => {
    try {
      // await handleRevealLetter();
    } catch (error) {
      console.error("Failed to showCocktailInfo:", error);
    }
  };

  const helpButtonsStyle = {
    display: "flex",
    fontWeight: "bold",
    flexDirection: "row",
    justifyContent: "center",
    gap: "25px",
    marginTop: "25px",
  };
  return (
    <Box sx={helpButtonsStyle}>
      <CustomButton size="small" onClick={skipRound}>
        Skip
      </CustomButton>
      <CustomButton size="small" onClick={revealLetter}>
        Reveal Letters
      </CustomButton>
      <CustomButton size="small" onClick={showCocktailInfo}>
        Show Info
      </CustomButton>
      <CustomButton size="small" color="error" onClick={finishGame}>
        Finish Game
      </CustomButton>
    </Box>
  );
};

export default HelpButtons;
