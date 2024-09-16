import { Box } from "@mui/material";
import React from "react";
import CustomButton from "./CustomButton";
import { useGameContext } from "../context/GameContext";

const HelpButtons: React.FC = () => {
  const { handleSkipRound } = useGameContext();

  const skipRound = async () => {
    try {
      await handleSkipRound();
    } catch (error) {
      console.error("Failed to handleSkipRound:", error);
    }
  };
  const helpButtonsStyle = {
    display: "flex",
    fontWeight: "bold",
    flexDirection: "row",
    justifyContent: "center",
    gap: "25px",
    marginTop: "25px",
  };
  return (
    <Box sx={helpButtonsStyle}>
      <CustomButton onClick={skipRound}>Skip</CustomButton>
      <CustomButton>Hint</CustomButton>
    </Box>
  );
};

export default HelpButtons;
