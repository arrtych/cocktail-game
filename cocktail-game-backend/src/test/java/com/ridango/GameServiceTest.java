package com.ridango;

import com.ridango.game.exceptions.LetterAlreadySelectedException;
import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.service.GameService;
import com.ridango.game.service.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private RestClient api;
    private Game game;

    private GameService gameService;

    private String player;

    @BeforeEach
    public void init() {
        api = new RestClient();
//        game = new Game();
        gameService = new GameService();
//        player = new Player("Tom");
        player = "Tom";

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

    @Test
    public void checkPlayerGuessTest() {
        this.gameService.startNewGame(player);

        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);
        this.gameService.getLastGame().setCocktail(cocktail);

        assertTrue(this.gameService.checkPlayerGuess("a", gameService.getLastGame().getPlayer().getId()));

        LetterAlreadySelectedException exception = assertThrows(
            LetterAlreadySelectedException.class,
            () -> {
                this.gameService.checkPlayerGuess("a", gameService.getLastGame().getPlayer().getId());
            }
        );
        assertEquals("Player has already chosen this letter.", exception.getMessage());

        assertFalse(this.gameService.checkPlayerGuess("x", gameService.getLastGame().getPlayer().getId()));

//        FalseAttemptException exception1 = assertThrows(
//                FalseAttemptException.class,
//                () -> {
//                    this.gameService.checkPlayerGuess("x", player.getId());
//                }
//        );
//        assertEquals("Wrong letter: "+"x"+"! Try again", exception1.getMessage());
    }

    @Test
    public void checkPlayerGuessFullWordCorrectWithOneMistakeTest() {
        gameService.startNewGame(player);

        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);
        gameService.getLastGame().setCocktail(cocktail);
        int playerId = gameService.getLastGame().getPlayer().getId();
        assertTrue(gameService.checkPlayerGuess("a", playerId));
        assertFalse(gameService.checkPlayerGuess("h",playerId));
        assertTrue(gameService.checkPlayerGuess("m", playerId));
        assertTrue(gameService.checkPlayerGuess("r", playerId));
        assertTrue(gameService.checkPlayerGuess("g", playerId));
        assertTrue(gameService.checkPlayerGuess("i", playerId));
        assertTrue(gameService.checkPlayerGuess("t", playerId));

        assertEquals(gameService.getLastGame().getScore(), 4);
//        System.out.println(this.gameService.getLastGame());
    }


    @Test
    public void showHintTest() {
        gameService.startNewGame(player);

        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);
        gameService.getLastGame().setCocktail(cocktail);
        gameService.revealNextLetter(gameService.getLastGame());

        assertEquals("a",gameService.getLastGame().getPlayerGuess().get(1));
        assertEquals("a",gameService.getLastGame().getPlayerGuess().get(4));
        assertEquals("a",gameService.getLastGame().getPlayerGuess().get(8));

        gameService.revealNextLetter(gameService.getLastGame());
        assertEquals("r",gameService.getLastGame().getPlayerGuess().get(2));
        assertEquals("r",gameService.getLastGame().getPlayerGuess().get(5));
        System.out.println(gameService);

    }




}
