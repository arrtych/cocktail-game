import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/game";
const start = "start";

interface PlayerProps {
  playerName: string;
}

export interface GuessLetterProps {
  playerId: number;
  letter: string;
}

export const startGame = async (player: PlayerProps) => {
  try {
    const requestBody = {
      playerName: player.playerName,
    };

    const response = await axios.post(`${API_BASE_URL}/start`, requestBody);

    // console.log("Game started:", response.data);
    return response.data;
  } catch (error) {
    console.error("Error starting the game:", error);
    throw error;
  }
};

// http://localhost:8080/api/game/start // post

// http://localhost:8080/api/game/{playerId}/guess?letter=m //put

// http://localhost:8080/api/game/state // get

// http://localhost:8080/api/game/skip // put

// http://localhost:8080/api/game/end // post

// @PutMapping("/{playerId}/guess")

export const guessLetter = async (props: GuessLetterProps) => {
  try {
    const playerId = props.playerId;
    const letter = props.letter;
    const response = await axios.put(
      `${API_BASE_URL}/${playerId}/guess?letter=${letter}`
    );

    return response.data;
  } catch (error) {
    console.error("Error guessLetter:", error);
    throw error;
  }
};

export const skipRound = async () => {
  try {
    const response = await axios.put(`${API_BASE_URL}/skip`);
    return response.data;
  } catch (error) {
    console.error("Error skipRound:", error);
    throw error;
  }
};

export const finishGame = async () => {
  try {
    const response = await axios.put(`${API_BASE_URL}/finish-game`);
    return response.data;
  } catch (error) {
    console.error("Error finishGame:", error);
    throw error;
  }
};

export const revealLetter = async () => {
  try {
    const response = await axios.put(`${API_BASE_URL}/reveal-letter`);
    return response.data;
  } catch (error) {
    console.error("Error revealLetter:", error);
    throw error;
  }
};
