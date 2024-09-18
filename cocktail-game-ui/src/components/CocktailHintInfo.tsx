import { Box } from "@mui/material";
import React from "react";
import { useGameContext } from "../context/GameContext";
import Grid from "@mui/material/Grid2";

const CocktailHintInfo: React.FC = () => {
  const { game } = useGameContext();
  const ingredientStyle = {
    display: "inline-block",
  };
  return (
    <Box className="cocktail-hint-info">
      {game?.cocktailOpenInfo?.strCategory && (
        <p>
          <b>Category:</b> {game?.cocktailOpenInfo.strCategory}
        </p>
      )}

      {game?.cocktailOpenInfo?.strGlass && (
        <p>
          <b>Glass:</b> {game?.cocktailOpenInfo.strGlass}
        </p>
      )}

      {game?.cocktailOpenInfo?.strIngredient && (
        <>
          <b>Ingredients: </b>

          {game?.cocktailOpenInfo?.strIngredient.map(
            (letter: string, index: number) => (
              <p key={index} style={ingredientStyle}>
                {letter}
                {index !== game.cocktailOpenInfo.strIngredient.length - 1
                  ? ", "
                  : ""}
              </p>
            )
          )}
        </>
      )}
    </Box>
  );
};

export default CocktailHintInfo;
