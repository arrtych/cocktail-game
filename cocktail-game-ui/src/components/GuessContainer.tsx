import React from "react";
import LetterGrid from "./LetterGrid";
import Grid from "@mui/material/Grid2";
import GuessWord from "./GuessWord";
import { Box } from "@mui/material";

const GuessContainer: React.FC = () => {
  return (
    <Box className="guess-container">
      <Grid size={12} className="guess-Word">
        <GuessWord />
      </Grid>
      <Grid size={12} className="letter-grid">
        <LetterGrid />
      </Grid>
    </Box>
  );
};

export default GuessContainer;
