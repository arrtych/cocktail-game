import { TextField } from "@mui/material";
import Grid from "@mui/material/Grid2";
import React from "react";
import CustomButton from "./CustomButton";

const addPLayerStyles = {
  display: "flex",
  justifyContent: "center",
  gap: "2em",
  marginTop: "2em",
};

const AddPlayer: React.FC = () => {
  return (
    <Grid size={12} className="add-player" style={addPLayerStyles}>
      <TextField id="outlined-basic" label="Player name" variant="standard" />
      <CustomButton>Add player</CustomButton>
    </Grid>
  );
};

export default AddPlayer;
