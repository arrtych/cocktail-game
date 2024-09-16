import React from "react";
import LetterGrid from "./LetterGrid";
import Grid from "@mui/material/Grid2";
import GuessWord from "./GuessWord";
import { Box } from "@mui/material";
import { useGameContext } from "../context/GameContext";

const wordDescriptionStyle = {
  fontSize: "1em",
  marginLeft: "10px",
  marginRight: "10px",
  textAlign: "center",
  marginTop: "2.5em",
};

const GuessContainer: React.FC = () => {
  const { game } = useGameContext();
  return (
    <Box className="guess-container">
      {game && (
        <>
          <Grid
            size={12}
            className="word-description"
            sx={wordDescriptionStyle}
          >
            <p>{game.cocktail.strInstructions}</p>
          </Grid>
          <Grid size={12} className="guess-Word">
            <GuessWord />
          </Grid>
        </>
      )}

      <Grid size={12} className="letter-grid">
        <LetterGrid />
      </Grid>
    </Box>
  );
};

export default GuessContainer;
