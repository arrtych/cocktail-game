import React from "react";
import Grid from "@mui/material/Grid2";
import { Box } from "@mui/material";
import LetterGrid from "./LetterGrid";
import GuessWord from "./GuessWord";
import HelpButtons from "./HelpButtons";
import { useGameContext } from "../context/GameContext";
import CocktailHintInfo from "./CocktailHintInfo";

const GuessContainer: React.FC = () => {
  const { game } = useGameContext();

  const wordDescriptionStyle = {
    fontSize: "1em",
    textAlign: "center",
    marginTop: "2.5em",
  };

  return (
    <Box className="guess-container">
      {game && (
        <>
          <Grid
            size={12}
            className="word-description"
            sx={wordDescriptionStyle}
          >
            <p>{game?.cocktail?.strInstructions}</p>
          </Grid>

          <Grid size={12}>
            <HelpButtons />
          </Grid>
          <Grid size={12}>
            <GuessWord />
          </Grid>
          <Grid>
            <CocktailHintInfo />
          </Grid>
        </>
      )}

      <Grid size={12}>
        <LetterGrid />
      </Grid>
    </Box>
  );
};

export default GuessContainer;
