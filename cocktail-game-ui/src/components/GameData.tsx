import { Box } from "@mui/material";
import React from "react";
import Grid from "@mui/material/Grid2";
import { useGameContext } from "../context/GameContext";

const GameData: React.FC = () => {
  const gameDatastyle = {
    display: "flex",
    fontWeight: "bold",
    flexDirection: "row",
    justifyContent: "center",
    gap: "25px",
    marginTop: "25px",
  };

  const boxStyle = {
    display: "flex",
    gap: "10px",
  };

  const { game, player } = useGameContext();
  return (
    player && (
      <Grid size={12} className="name" sx={gameDatastyle}>
        <Box className="name" sx={boxStyle}>
          <p>Player:</p>
          <p>{player?.name}</p>
        </Box>
        <Box className="score" sx={boxStyle}>
          <p>Score:</p>
          <p>{game?.score}</p>
        </Box>
        <Box className="score" sx={boxStyle}>
          <p>Round:</p>
          <p>{game?.round}</p>
        </Box>
        <Box className="score" sx={boxStyle}>
          <p>Attemps left:</p>
          <p>{game?.attemptsLeft}</p>
        </Box>
      </Grid>
    )
  );
};

export default GameData;
