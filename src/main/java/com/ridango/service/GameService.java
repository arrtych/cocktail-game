package com.ridango.service;

import com.ridango.model.Cocktail;
import com.ridango.model.Game;
import com.ridango.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

//    private Game game =new Game();
    private RestClient api = new RestClient();

    private Map<Integer, Game> games = new HashMap<>();

    public GameService() {
        //setPlayer
        //updatePlayerScore
        //generate word,
        // save correct word - guessed cocktail
    }

    public Cocktail generateCocktail() {
        //todo: change later logic for getting
        // todo: get cocktail with name.length > 3
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);

        return cocktail;
    }

    public  List<char[]> divideWord(String word) {
        List<char[]> charactersList = Arrays.asList(word.toCharArray());


        return charactersList;
    }

    public Game  startGame() {
        Game game = new Game();
        Player player = new Player("Tim");
        game.setPlayer(player);
        games.put(game.getId(), game);

        Cocktail cocktail = this.generateCocktail();
        game.setWordToGuess(divideWord(cocktail.getStrDrink()));


/**
 *
 *         setWordToGuess
 *
 *         printWord (with empty chars)
 *
 *         player start guess
 *
 *         check if chars exists, and for dublicates, //nb: cant select char twice
 *                 calculates points
 *
 *                 (use hints)
 *
 *         check for win/lose
 *
 *                 (if word guessed setWordToGuess: clear and set next)
 *
 *         check for win/lose
 *
 */


        return game;
    }

    public Game getLastGame() {
        if (games.isEmpty()) {
            return null;
        }
        Optional<Integer> maxKey = games.keySet().stream().max(Integer::compareTo);
        return maxKey.map(games::get).orElse(null); // Return the game associated with the highest key
    }

    public void printWord() {
        for (char[] chars : this.getLastGame().getWordToGuess()) {
            for (char c : chars) {
                System.out.print("_ ");
            }
        }
//        System.out.println("t");

    }

    public boolean checkPlayerGuess(Character character) {
        boolean result = false;
//        if()
        return result;
    }

    public Game getGameById(int gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found for ID: " + gameId);
        }
        return game;
    }


}
