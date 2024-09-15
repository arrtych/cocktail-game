import { Box } from "@mui/material";
import React from "react";

export interface GuessWordProps {
  word?: string; //todo
}

const wordLength = 9;
const boxStyle = {
  display: "flex",
  justifyContent: "center",
  fontSize: "2em",
  fontWeight: "700",
  gap: "1em",
  marginTop: "2.5em",
};

const letterStyle = {};
const GuessWord: React.FC<GuessWordProps> = (props: GuessWordProps) => {
  return (
    <Box sx={boxStyle}>
      {Array(wordLength)
        .fill(null)
        .map((_, index) => (
          <Box key={index} sx={letterStyle} className="boox">
            _
          </Box>
        ))}
    </Box>
  );
};

export default GuessWord;
