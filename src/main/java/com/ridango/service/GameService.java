package com.ridango.service;

import com.ridango.model.Cocktail;
import com.ridango.model.Game;
import com.ridango.model.Player;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //todo: change later
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(1);
//        System.out.println(cocktails);
        return cocktail;
    }

    public void divideWord(String word) {
        List<char[]> charactersList = Arrays.asList(word.toCharArray());
        System.out.println("t");
    }

    public Game  startGame() {
        Game game = new Game();
        Player player = new Player("Tim");
        game.setPlayer(player);
        games.put(game.getId(), game);

        Cocktail cocktail = this.generateCocktail();
        divideWord(cocktail.getStrDrink());
        return game;
    }
    public Game getGameById(int gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found for ID: " + gameId);
        }
        return game;
    }


}
