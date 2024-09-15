import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/game";
const start = "start";

interface PlayerProps {
  id?: number;
  name: string;
  score?: number;
}

export const startGame = async (player: PlayerProps) => {
  try {
    const requestBody = {
      // id: player.id,
      name: player.name,
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
