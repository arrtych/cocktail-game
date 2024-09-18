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

import java.util.Arrays;
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
        gameService = new GameService();
        player = "Tom";

    }

    @Test
    public void generateCocktailTest() {
        this.gameService.startNewGame(player);
        Game lastGame = gameService.getLastGame();

        Cocktail cocktail = lastGame.getCocktail();
        Assertions.assertNotNull(cocktail);
        assertTrue(cocktail instanceof Cocktail);
        assertTrue(cocktail.getStrInstructions() instanceof String);
        assertTrue(cocktail.getIdDrink() instanceof String);

        lastGame.nextRound();
        Cocktail newCocktail = this.gameService.generateCocktail();

        assertTrue(!lastGame.getCocktail().equals(newCocktail));
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

        //Override gameCocktail to "margarita" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);

        assertEquals(lastGame.getWordToGuess().size(), lastGame.getPlayerGuess().size());
        String letter = lastGame.getPlayerGuess().get(1);
        assertEquals(letter, "_");
    }

    @Test
    public void wordWithSpacesToArrayForGuessTest() {
        this.gameService.startNewGame(player);
        Game lastGame = gameService.getLastGame();

        //Override gameCocktail to "Old Pal" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("Old").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);

        String letter = lastGame.getPlayerGuess().get(3);
        assertEquals(letter, " "); //4th symbol is space

    }

    @Test
    public void wordWithSymbolToArrayForGuessTest() {
        this.gameService.startNewGame(player);
        Game lastGame = gameService.getLastGame();

        //Override gameCocktail to <"idDrink": "17222", "strDrink": "A1">, instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("&").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);

        String letter = lastGame.getPlayerGuess().get(1);
        assertEquals(letter, "1"); //2th symbol is space


    }

    @Test
    public void checkPlayerGuessTest() {
        this.gameService.startNewGame(player);

        //Override gameCocktail to "margarita" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);


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

        //Override gameCocktail to "margarita" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);


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
    }


    @Test
    public void revealNextLetterTest() {
        gameService.startNewGame(player);

        Game lastGame =  gameService.getLastGame();
        //Override gameCocktail to "margarita" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);

        gameService.revealNextLetter();

        assertEquals("a",lastGame.getPlayerGuess().get(1));
        assertEquals("a",lastGame.getPlayerGuess().get(4));
        assertEquals("a",lastGame.getPlayerGuess().get(8));
        assertEquals(4, lastGame.getAttemptsLeft());

        gameService.revealNextLetter();
        assertEquals("r",lastGame.getPlayerGuess().get(2));
        assertEquals("r",lastGame.getPlayerGuess().get(5));
        assertEquals(3, lastGame.getAttemptsLeft());
//        System.out.println(gameService);

    }


    @Test
    public void showCocktailInfoWhenPlayerNeedHintsTest() {
        gameService.startNewGame(player);

        //Override gameCocktail to "margarita" instead of sets from api.
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        this.gameService.getLastGame().setCocktailDB(Arrays.asList(cocktails.get(0)));
        Cocktail cocktail = this.gameService.generateCocktail();
        this.gameService.setGameCocktail(cocktail);


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





}
