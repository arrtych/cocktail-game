package com.ridango;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.service.GameService;
import com.ridango.game.service.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class GameServiceTest {

    private RestClient api;
    private Game game;

    private GameService gameService;

    private Player player;

    @BeforeEach
    public void init() {
//        api = new RestClient();
//        game = new Game();
        gameService = new GameService();
        player = new Player("Tom");

    }

    @Test
    public void generateCocktailTest() {
        Cocktail cocktail = gameService.generateCocktail();
        Assertions.assertNotNull(cocktail);
        assertTrue(cocktail instanceof Cocktail);
        assertTrue(cocktail.getStrInstructions() instanceof String);
        assertTrue(cocktail.getIdDrink() instanceof String);
    }

    @Test
    public void startGameTest() {
        this.gameService.startNewGame(player);
        Game lastActiveGame = gameService.getLastGame();
        assertTrue(lastActiveGame.isActive());
        assertTrue(lastActiveGame.getWordToGuess() instanceof List<String>);
    }

    @Test
    public void wordToArrayForGuessTest() {
        this.gameService.startNewGame(player);
        Game lastGame = gameService.getLastGame();
        assertEquals(lastGame.getWordToGuess().size(), lastGame.getPlayerGuess().size());
        String letter = lastGame.getPlayerGuess().get(1);
        assertEquals(letter, "_");
    }


//    @Test
//    public void printWordTest() {
//        this.gameService.startGame();
//        this.gameService.printWord();
//    }
}
