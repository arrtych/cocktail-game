import { Box } from "@mui/material";
import React from "react";

const LetterGrid: React.FC = () => {
  const letters = "abcdefghijklmnopqrstvwyz";

  const letterGridStyle = {
    display: "flex",
    justifyContent: "center",
    flexWrap: "wrap",
  };

  const letterStyle = {
    display: "inline-block",
    margin: "10px",
    border: "1px solid black",
    flex: "1 1 calc(10% - 10px)",
    boxSizing: "border-box",
    textAlign: "center",
    fontSize: "2em",
    fontWeight: "bold",
  };

  return (
    <Box sx={letterGridStyle}>
      {letters.split("").map((letter, index) => (
        <Box key={index} sx={letterStyle}>
          {letter.toUpperCase()}
        </Box>
      ))}
    </Box>
  );
};

export default LetterGrid;
