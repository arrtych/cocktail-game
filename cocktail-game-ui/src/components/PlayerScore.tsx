import { Box } from "@mui/material";
import React from "react";
import Grid from "@mui/material/Grid2";

interface PlayerScoreProps {
  name?: string;
  score?: number;
}
const playerScoreStyle = {
  display: "flex",
  //   gap: "5px",
  fontWeight: "bold",
  flexDirection: "row",
  justifyContent: "center",
  gap: "25px",
  marginTop: "25px",
};

const boxStyle = {
  display: "flex",
  gap: "10px",
};
const PlayerScore: React.FC<PlayerScoreProps> = (props: PlayerScoreProps) => {
  return (
    // style={{display: "none"}}
    <Grid size={12} className="name" sx={playerScoreStyle}>
      <Box className="name" sx={boxStyle}>
        <p>Player:</p>
        <p>Tim</p>
      </Box>
      <Box className="score" sx={boxStyle}>
        <p>Score:</p>
        <p>0</p>
      </Box>
    </Grid>
  );
};

export default PlayerScore;
