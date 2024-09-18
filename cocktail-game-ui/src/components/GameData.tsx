import { Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import Grid from "@mui/material/Grid2";
import { useGameContext } from "../context/GameContext";

const GameData: React.FC = () => {
  const { game, player } = useGameContext();
  const [highlightClass, setHighlightClass] = useState("");

  const highlightParagraph = () => {
    setHighlightClass("highlight");

    setTimeout(() => {
      setHighlightClass("");
    }, 5000);
  };

  useEffect(() => {
    if (
      game?.attemptsLeft !== undefined &&
      game?.attemptsLeft < 5 &&
      game.active
    ) {
      highlightParagraph();
    }
  }, [game?.attemptsLeft]);

  const gameDataStyle = {
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

  return (
    player && (
      <Grid size={12} className="game-data" sx={gameDataStyle}>
        <Box className="name" sx={boxStyle}>
          <p>Player:</p>
          <p>{player?.name}</p>
        </Box>
        <Box className="score" sx={boxStyle}>
          <p>Score:</p>
          <p>{game?.score}</p>
        </Box>
        <Box className="round" sx={boxStyle}>
          <p>Round:</p>
          <p>{game?.round}</p>
        </Box>
        <Box className={`attemps ${highlightClass}`} sx={boxStyle}>
          <p>Attemps left:</p>
          <p>{game?.attemptsLeft}</p>
        </Box>
      </Grid>
    )
  );
};

export default GameData;
