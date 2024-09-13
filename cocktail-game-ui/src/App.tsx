import React from "react";
import "./App.css";
import { GameContextProvider } from "./context/GameContext";
import Container from "./components/Container";

function App() {
  return (
    <GameContextProvider>
      <div className="App">
        React
        <Container></Container>
      </div>
    </GameContextProvider>
  );
}

export default App;
