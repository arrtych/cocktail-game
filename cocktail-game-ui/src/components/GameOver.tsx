import { Box, Typography } from "@mui/material";
import React from "react";

const GameOver: React.FC = () => {
  const gameOverStyle = {
    display: "inline-block",
    color: "red",
    fontWeight: "bold",
  };
  const gameOverBoxStyle = {
    margin: "7em auto",
  };
  return (
    <Box sx={gameOverBoxStyle}>
      <Typography variant="h5" sx={gameOverStyle}>
        Game Over
      </Typography>
    </Box>
  );
};

export default GameOver;
