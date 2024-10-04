## Assignment

- Build a simple game of guess the cocktail using public API https://www.thecocktaildb.com/api.php

- Game rules:

  - Random cocktail with instructions (strInstructions) is shown to the player together with number of letter in the name of the cocktail (e.g. "Mojito" -> "\_ \_ \_ \_ \_ \_")
  - Player must guess the name of the cocktail
  - Player can skip the round if he doesn't know the answer to get more hints about the cocktail
  - Player has 5 attempts to guess the name of the cocktail
  - If player answers correctly the game continues with a new random cocktail and score is increased by number of attempts left
  - If player answers wrongly or skips round the game shows:
    - Name of the cocktail with some new random letters revealed (e.g. "\_ \_ \_ \_ \_ _" -> " _ _ j _ \_ _" -> " _ _ j _ _ o" -> "M _ j \_ _ o" -> "M _ ji _ o" -> "M _ jito" -> "Mojito") (For longer cocktails more letters can be revealed than one)
    - Reveal additional info about the cocktail (e.g. category, glass, ingredients, picture)
    - Number of attempts left
  - If player fails to guess the cocktail after 5 attempts the game ends and high score is updated
  - In one game same cocktail shouldn't appear twice

- Resources:
  - Instructions on how to use the API are written here: https://www.thecocktaildb.com/api.php

# Cocktail Game

This is a full-stack application consisting of a React frontend and a Spring Boot backend.

## 🚀 Getting Started

### Prerequisites

Before you dive into the fun, ensure you have the following installed:

- [Docker](https://docs.docker.com/get-docker/) - The containerization platform that makes it easy to run applications in isolated environments.
- [Docker Compose](https://docs.docker.com/compose/install/) - A tool for defining and running multi-container Docker applications.

### Step-by-Step Instructions

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/arrtych/cocktail-game.git

   ```

2. **Build and Start the Application**:
   ```bash
    docker-compose up --build
   ```
3. **Access the Application**:

- Frontend: Open your web browser and navigate to http://localhost:3000.
- Backend: The backend API will be accessible at http://localhost:8080
- Backend Swagger: http://localhost:8080/swagger-ui/index.html#/

**Additional Commands**

- **Stopping the Application**:
  ```bash
   docker-compose down
  ```
- **Rebuilding the Application**:

  ```bash
   docker-compose up --build

  ```

📚 Project Structure

- _cocktail-game-backend_: The Spring Boot backend application.
- _cocktail-game-ui_: The React frontend application.
