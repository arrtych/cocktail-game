import { Box } from "@mui/material";
import React, { useState } from "react";
import CustomButton from "./CustomButton";
import { useGameContext } from "../context/GameContext";

const HelpButtons: React.FC = () => {
  const {
    handleSkipRound,
    handleFinishGame,
    handleRevealLetter,
    handleShowCocktailHintInfo,
  } = useGameContext();

  const [isCategoryShown, setIsCategoryShown] = useState(false);
  const [isGlassShown, setIsGlassShown] = useState(false);
  const [isIngredientsShown, setIngredientsShown] = useState(false);

  const skipRound = async () => {
    try {
      await handleSkipRound();

      setIngredientsShown(false);
      setIsCategoryShown(false);
      setIsGlassShown(false);
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

  const showCocktailInfo = async (infoType: string) => {
    try {
      await handleShowCocktailHintInfo(infoType);
      if (infoType === "category") {
        setIsCategoryShown(true);
      }
      if (infoType === "glass") {
        setIsGlassShown(true);
      }
      if (infoType === "ingredients") {
        setIngredientsShown(true);
      }
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
    <Box sx={helpButtonsStyle} className="help-buttons">
      <CustomButton onClick={skipRound}>Skip</CustomButton>

      <CustomButton onClick={revealLetter}>Reveal Letters</CustomButton>

      <CustomButton
        onClick={() => showCocktailInfo("category")}
        disabled={isCategoryShown}
      >
        Show Category
      </CustomButton>
      <CustomButton
        onClick={() => showCocktailInfo("glass")}
        disabled={isGlassShown}
      >
        Show Glass
      </CustomButton>

      <CustomButton
        onClick={() => showCocktailInfo("ingredients")}
        disabled={isIngredientsShown}
      >
        Show Ingredients
      </CustomButton>

      <CustomButton color="error" onClick={finishGame}>
        Finish Game
      </CustomButton>
    </Box>
  );
};

export default HelpButtons;
