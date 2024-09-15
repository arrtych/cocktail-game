import React from "react";
import LetterGrid from "./LetterGrid";
import Grid from "@mui/material/Grid2";
import GuessWord from "./GuessWord";
import { Box } from "@mui/material";

const wordDescriptionStyle = {
  fontSize: "1em",
  marginLeft: "10px",
  marginRight: "10px",
  textAlign: "center",
  marginTop: "2.5em",
};

const GuessContainer: React.FC = () => {
  return (
    <Box className="guess-container">
      <Grid size={12} className="word-description" sx={wordDescriptionStyle}>
        <p>
          Rub the rim of the glass with the lime slice to make the salt stick to
          it. Take care to moisten only the outer rim and sprinkle the salt on
          it. The salt should present to the lips of the imbiber and never mix
          into the cocktail. Shake the other ingredients with ice, then
          carefully pour into the glass.
        </p>
      </Grid>
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
