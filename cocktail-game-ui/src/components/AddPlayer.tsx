import { TextField } from "@mui/material";
import Grid from "@mui/material/Grid2";
import React, { useState } from "react";
import CustomButton from "./CustomButton";
import { useGameContext } from "../context/GameContext";
import { startGame } from "../service/ApiService";

const AddPlayer: React.FC = () => {
  const [playerName, setPlayerName] = useState<string>("");
  const { setGame, setPlayer, handleStartGame, game } = useGameContext();
  const [isLoading, setIsLoading] = useState(false);

  const handleAddPlayer = async () => {
    if (playerName.trim() === "") return;

    try {
      setIsLoading(true);
      const game = await startGame({ playerName: playerName }); //todo: fix and test
      setGame(game);
      setPlayer(game.player);

      console.log("Game started: Guess:", game?.cocktail);
    } catch (error) {
      console.error("Failed to start the game:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const addPLayerStyles = {
    display: "flex",
    justifyContent: "center",
    gap: "2em",
    marginTop: "2em",
  };

  const loaderGridStyle = {
    display: "flex",
    justifyContent: "center",
    marginTop: "4em",
  };

  return (
    <>
      <Grid size={12} className="add-player" style={addPLayerStyles}>
        <TextField
          id="outlined-basic"
          label="Player name"
          variant="standard"
          onChange={(e) => setPlayerName(e.target.value)}
        />
        <CustomButton size="medium" onClick={handleAddPlayer}>
          Add player
        </CustomButton>
      </Grid>

      {/* <Grid size={12}>{isLoading && <div className="loader"></div>}</Grid> */}

      {isLoading && (
        <Grid size={12} sx={loaderGridStyle}>
          <div className="loader"></div>
        </Grid>
      )}
    </>
  );
};

export default AddPlayer;
