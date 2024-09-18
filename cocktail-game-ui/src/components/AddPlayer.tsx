import { TextField } from "@mui/material";
import Grid from "@mui/material/Grid2";
import React, { useState } from "react";
import CustomButton from "./CustomButton";
import { useGameContext } from "../context/GameContext";
import { startGame } from "../service/ApiService";

const AddPlayer: React.FC = () => {
  const [playerName, setPlayerName] = useState<string>("");
  const { setGame, setPlayer, handleStartGame } = useGameContext();

  const handleAddPlayer = async () => {
    if (playerName.trim() === "") return;

    try {
      const game = await startGame({ playerName: playerName }); //todo: fix and test
      setGame(game);
      setPlayer(game.player);
    } catch (error) {
      console.error("Failed to start the game:", error);
    }
  };

  const addPLayerStyles = {
    display: "flex",
    justifyContent: "center",
    gap: "2em",
    marginTop: "2em",
  };

  return (
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
  );
};

export default AddPlayer;
