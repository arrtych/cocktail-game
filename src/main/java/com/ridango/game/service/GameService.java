package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;

@Service
public class GameService {

//    private Game game =new Game();
    private RestClient api = new RestClient();

    private Map<Integer, Game> games = new HashMap<>();

    public Map<Integer, Game> getGames() {
        return games;
    }

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

    public  List<String> wordToArray(String word) {
        return Arrays.stream(word.split(""))
                .collect(Collectors.toList());
    }

    public List<String> wordToArrayForGuess(List<String> originalList) {
        List<String> result = new ArrayList<>();
        for (String word : originalList) {
            char[] underscores = new char[word.length()];
            Arrays.fill(underscores, '_');
            result.add(new String(underscores));
        }
        return result;
    }



    public Game  startGame() {
        Game game = new Game();
        Player player = new Player("Tim");
        game.setPlayer(player);
        games.put(game.getId(), game);

        Cocktail cocktail = this.generateCocktail();
        game.setWordToGuess(wordToArray(cocktail.getStrDrink()));


/**
 *
 *         setWordToGuess  // optimize api requests.(change api )
 *         printWord (with empty chars)
 *         player start guess
 *         check if chars exists, and for dublicates, //nb: cant select char twice
 *                 calculates points
 *                 (use hints)
 *         check for win/lose
 *                 (if word guessed setWordToGuess: clear and set next)
 *         check for win/lose
 *
 */


        return game;
    }

    public Game startNewGame(Player player) {
        Game game = new Game();
        game.setPlayer(player);
        games.put(game.getId(), game);

        Cocktail cocktail = this.generateCocktail();
        game.setCocktail(cocktail);
        game.setWordToGuess(wordToArray(cocktail.getStrDrink()));
        game.setPlayerGuess(wordToArrayForGuess(game.getWordToGuess()));

        return game;
    }



    public Game getLastGame() {
        if (games.isEmpty()) {
            return null;
        }
        Optional<Integer> maxKey = games.keySet().stream().max(Integer::compareTo);
        return maxKey.map(games::get).orElse(null);
    }

    public void printWord() {
//        for (char[] chars : this.getLastGame().getWordToGuess()) {
//            for (char c : chars) {
//                System.out.print("_");
//            }
//        }
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
