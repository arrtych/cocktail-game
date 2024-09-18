import { Box, Typography } from "@mui/material";
import React from "react";
import { useGameContext } from "../context/GameContext";

const GameOver: React.FC = () => {
  const { game } = useGameContext();

  const gameOverStyle = {
    display: "inline-block",
    color: "red",
    fontWeight: "bold",
  };
  const gameOverBoxStyle = {
    margin: "7em auto",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    gap: "1em",
  };

  const lastWordStyle = {
    display: "inline-block",
    fontWeight: "bold",
  };
  return (
    <Box sx={gameOverBoxStyle}>
      <Typography variant="h6" sx={gameOverStyle}>
        Game Over
      </Typography>

      <Typography variant="h6" style={lastWordStyle}>
        Unguessed word :{game.cocktail.strDrink}
      </Typography>
    </Box>
  );
};

export default GameOver;
