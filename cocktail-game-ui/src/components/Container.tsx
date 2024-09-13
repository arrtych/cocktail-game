import React, { useEffect } from "react";
import { useGameContext } from "../context/GameContext";

const Container: React.FC = () => {
  const { game, handleStartGame } = useGameContext();
  useEffect(() => {
    handleStartGame();
  }, []);

  return <div>Container</div>;
};

export default Container;
