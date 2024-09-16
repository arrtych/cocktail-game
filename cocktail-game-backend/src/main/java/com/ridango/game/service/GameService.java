package com.ridango.game.service;

import com.ridango.game.exceptions.GameOverException;
import com.ridango.game.exceptions.LetterAlreadySelectedException;
import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {


    private RestClient api = new RestClient();

    private Map<Integer, Game> games = new HashMap<>();

    private static final int MAX_ATTEMPTS = 5;

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
        //todo: In one game same cocktail shouldn't appear twice
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        Cocktail cocktail = cocktails.get(0);

        return cocktail;
    }

    public  List<String> wordToList(String word) {
        return Arrays.stream(word.split(""))
                .map(String::toLowerCase)
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
        game.setWordToGuess(wordToList(cocktail.getStrDrink()));


/**
 *
 *         setWordToGuess  // optimize api requests.(change api )
 *         printWord (with empty chars) +
 *         player start guess +
 *         check if chars exists, and for dublicates, //nb: cant select char twice +
 *                 calculates points
 *                 (use hints)
 *         check for win/lose
 *                 (if word guessed setWordToGuess: clear and set next)
 *         check for win/lose
 *
 */


        return game;
    }

    public Game startNewGame(String playerName) {
        Game game = new Game();
        Player player = new Player(playerName);
        game.setPlayer(player);
        games.put(game.getId(), game);

        Cocktail cocktail = this.generateCocktail();
        game.setCocktail(cocktail);
        game.setAttemptsLeft(MAX_ATTEMPTS);
        game.setWordToGuess(wordToList(cocktail.getStrDrink()));
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

    public boolean checkPlayerGuess(String letter, int playerId) {
        boolean result = false;
        Game currentGame = this.getLastGame();
        if (currentGame == null || playerId != currentGame.getPlayer().getId()) {
            throw new IllegalArgumentException("No game found for player");
        }
        if (playerId == currentGame.getPlayer().getId()) {
           if(getLastGame().getWordToGuess().contains(letter)) { // if letter exist in word
               if(!currentGame.getSelectedLetters().contains(letter)) { // if player not selected this letter yet
                   for (int i = 0; i < currentGame.getWordToGuess().size(); i++) {
                       if (currentGame.getWordToGuess().get(i).equals(letter)) { //if current letter equals search letter
                           currentGame.getPlayerGuess().set(i, letter);
                       }
                   }
                   currentGame.getSelectedLetters().add(letter); //save player search letter

                   // Check if the word is completely guessed
                   if (!currentGame.getPlayerGuess().contains("_")) {
                       currentGame.setScore(currentGame.getScore() + currentGame.getAttemptsLeft()); // Increase score
                       currentGame.getPlayer().setScore(currentGame.getScore());
                       System.out.println(getLastGame());
                       this.startNewRound(currentGame); // Start a new round with a new cocktail
                   }
                   result = true;
               } else {
                   throw new LetterAlreadySelectedException("Player has already chosen this letter.");
               }

           } else {
               //Answer wrong -> show hint
               currentGame.setAttemptsLeft(currentGame.getAttemptsLeft() - 1);
               if (currentGame.getAttemptsLeft() == 0) {
                   currentGame.getPlayer().setScore(currentGame.getScore());
                   currentGame.endGame();
                   throw new GameOverException("Game over! You have no more attempts.");
               }
           }
        }
        return result;
    }

    public void startNewRound(Game currentGame) {
        // Reset the game with a new cocktail
        Cocktail newCocktail = this.generateCocktail();
        currentGame.setCocktail(newCocktail);
        currentGame.setWordToGuess(wordToList(newCocktail.getStrDrink()));
        currentGame.setPlayerGuess(wordToArrayForGuess(currentGame.getWordToGuess()));
        currentGame.setSelectedLetters(new ArrayList<>());
        currentGame.setAttemptsLeft(MAX_ATTEMPTS); // Reset attempts for the new cocktail
    }

    public void skipRound() {//todo: test
        this.getLastGame().setAttemptsLeft(this.getLastGame().getAttemptsLeft() - 1);
        this.startNewRound(this.getLastGame());
        //show hint
    }

    public void revealNextLetter(Game currentGame) {
//        String possibleChars = "12345679abcdefghijklmnopqrstvwyz";
        List<String> popularLetters = Arrays.asList("e", "a", "r", "i", "o", "t", "n", "s", "l", "c");
        //todo: add random
        for (String letter : popularLetters) {
            //If popularLetter not yet selected and word contains this letter
            if (!currentGame.getSelectedLetters().contains(letter) && currentGame.getWordToGuess().contains(letter)) {
                for (int i = 0; i < currentGame.getWordToGuess().size(); i++) {
                    String currentLetter = currentGame.getWordToGuess().get(i);
                    if (currentLetter.equalsIgnoreCase(letter)) {
                        // Reveal the letter in the player's guess
                        currentGame.getPlayerGuess().set(i, letter);
                    }
                }
                currentGame.getSelectedLetters().add(letter);
                // Break after revealing the first unselected letter
                break;
            }
        }
    }

    public Game getGameById(int gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found for ID: " + gameId);
        }
        return game;
    }


}
