import React, { useEffect } from "react";
import { useGameContext } from "../context/GameContext";
import { Box, TextField, Typography } from "@mui/material";
import Grid from "@mui/material/Grid2";
import CustomButton from "./CustomButton";
import AddPlayer from "./AddPlayer";
import GuessContainer from "./GuessContainer";
import GuessWord from "./GuessWord";
import PlayerScore from "./PlayerScore";

const Container: React.FC = () => {
  const { game } = useGameContext();
  useEffect(() => {
    // handleStartGame();
  }, []);

  const mainSectionStyles = { width: "900px", margin: "0 auto" };

  return (
    <Box component="section" style={mainSectionStyles}>
      <Grid container sx={{ p: 4 }} className="container">
        <Grid size={12} className="title" sx={{ textAlign: "center" }}>
          <Typography variant="h4" sx={{ display: "inline-block" }}>
            Guess the name of the cocktail
          </Typography>
        </Grid>

        {/* Show Add player if game not created yet */}
        {!game && <AddPlayer />}

        <PlayerScore />
        <GuessContainer />
      </Grid>
    </Box>
  );
};

export default Container;
