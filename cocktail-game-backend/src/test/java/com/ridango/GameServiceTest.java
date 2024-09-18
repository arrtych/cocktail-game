package com.ridango;

import com.ridango.game.exceptions.LetterAlreadySelectedException;
import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.service.GameService;
import com.ridango.game.service.RestClient;
import com.ridango.game.types.ApiKeyStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(gameService.getLastGame().getPlayer().getScore(), 4);
//        System.out.println(this.gameService.getLastGame());
    }


    @Test
    public void revealNextLetterTest() {
        gameService.startNewGame(player);

        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);
        gameService.getLastGame().setCocktail(cocktail);
        Game lastGame =  gameService.getLastGame();
        gameService.revealNextLetter();

        assertEquals("a",lastGame.getPlayerGuess().get(1));
        assertEquals("a",lastGame.getPlayerGuess().get(4));
        assertEquals("a",lastGame.getPlayerGuess().get(8));
        assertEquals(4, lastGame.getAttemptsLeft());

        gameService.revealNextLetter();
        assertEquals("r",lastGame.getPlayerGuess().get(2));
        assertEquals("r",lastGame.getPlayerGuess().get(5));
        assertEquals(3, lastGame.getAttemptsLeft());
        System.out.println(gameService);

    }


    @Test
    public void showCocktailInfoWhenPlayerNeedHintsTest() {
        gameService.startNewGame(player);

        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);
        gameService.getLastGame().setCocktail(cocktail);
        assertTrue(gameService.showCocktailHintInfo(cocktail, ApiKeyStr.CATEGORY));
        assertEquals("Ordinary Drink", gameService.getLastGame().getCocktailOpenInfo().get("strCategory"));

        assertFalse(gameService.showCocktailHintInfo(cocktail, ApiKeyStr.ALCOHOLIC));

        assertTrue(gameService.showCocktailHintInfo(cocktail, ApiKeyStr.INGREDIENT));
        List<String> ingredients = (List<String>) gameService.getLastGame().getCocktailOpenInfo().get("strIngredient");
        assertEquals(4, ingredients.size());
        assertEquals("Tequila", ingredients.get(0));

        assertTrue(gameService.showCocktailHintInfo(cocktail, ApiKeyStr.GLASS));
        assertEquals("Cocktail glass", gameService.getLastGame().getCocktailOpenInfo().get("strGlass"));



    }

//    this.showCocktailInfo(currentGame.getCocktail(), INGREDIENT);




}
