import React, { useEffect } from "react";
import { useGameContext } from "../context/GameContext";
import { Box, Typography } from "@mui/material";
import Grid from "@mui/material/Grid2";
import AddPlayer from "./AddPlayer";
import GuessContainer from "./GuessContainer";
import GameData from "./GameData";
import GameOver from "./GameOver";

const Container: React.FC = () => {
  const { game } = useGameContext();
  // useEffect(() => {
  //   // handleStartGame();
  // }, []);

  const mainSectionStyles = { width: "900px", margin: "0 auto" };

  return (
    <Box component="section" style={mainSectionStyles}>
      <Grid container sx={{ p: 4 }} className="container">
        <Grid size={12} className="title" sx={{ textAlign: "center" }}>
          <Typography variant="h4" sx={{ display: "inline-block" }}>
            Guess the name of the cocktail
          </Typography>
        </Grid>

        {/* Show Add player if game not created yet or finished */}
        {(!game || !game?.active) && <AddPlayer />}

        <GameData />

        {game && !game?.active ? <GameOver /> : <GuessContainer />}
      </Grid>
    </Box>
  );
};

export default Container;
