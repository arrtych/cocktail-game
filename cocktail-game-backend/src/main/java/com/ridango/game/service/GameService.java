package com.ridango.game.service;

import com.ridango.game.exceptions.LetterAlreadySelectedException;
import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.types.ApiKeyStr;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class GameService {


    private RestClient api = new RestClient();

    private Map<Integer, Game> games = new HashMap<>();

    private static final int MAX_ATTEMPTS = 5;

    /**
     * Each round means new word for guess. Player cant skip if no available words.
     */
    private static final int MAX_ROUNDS = 3;


    public GameService() {
    }

    public Map<Integer, Game> getGames() {
        return games;
    }

    /**
     * Get new Cocktail from game db by game round number
     * @return
     */
    public Cocktail generateCocktail() {
        Game currentGame = this.getLastGame();
        if(currentGame.getCocktailDB().size() == 1) {
            return currentGame.getCocktailDB().get(0);
        } else {
            return currentGame.getCocktailDB().get(currentGame.getRound() - 1);
        }
    }



    public  List<String> wordToList(String word) {
        return Arrays.stream(word.split(""))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<String> wordToArrayForGuess(List<String> originalList) {
        List<String> result = new ArrayList<>();
        for (String word : originalList) {
            StringBuilder underscoredWord = new StringBuilder();
            for (char c : word.toCharArray()) {
                // If character is a space or symbol, add the character, otherwise add an underscore
                if (Character.isLetter(c)) {
                    underscoredWord.append('_');
                } else {
                    underscoredWord.append(c);
                }
            }
            result.add(underscoredWord.toString());
        }
        return result;
    }





    public Game startNewGame(String playerName) {
        Game game = new Game();
        Player player = new Player(playerName);
        game.setPlayer(player);
        game.setCocktailDB(api.getRandomCocktailsFromDB(MAX_ROUNDS));
        games.put(game.getId(), game);


        Cocktail cocktail = this.generateCocktail();
        game.setAttemptsLeft(MAX_ATTEMPTS);
        this.setGameCocktail(cocktail);

//        game.getGameCocktails().add(cocktail);
//        game.setCocktail(cocktail);
//        game.setWordToGuess(wordToList(cocktail.getStrDrink()));
//        game.setPlayerGuess(wordToArrayForGuess(game.getWordToGuess()));


        return game;
    }

    public void setGameCocktail(Cocktail cocktail) {
        Game currentGame = this.getLastGame();
        currentGame.getGameCocktails().add(cocktail);
        currentGame.setCocktail(cocktail);
        currentGame.setWordToGuess(wordToList(cocktail.getStrDrink()));
        currentGame.setPlayerGuess(wordToArrayForGuess(currentGame.getWordToGuess()));
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

                       this.startNewRound(currentGame); // Start a new round with a new cocktail
                   }
                   result = true;
               } else {
                   throw new LetterAlreadySelectedException("Player has already chosen this letter.");
               }

           } else {
               //Answer wrong -> show hint
               currentGame.setAttemptsLeft(currentGame.getAttemptsLeft() - 1);
               currentGame.getSelectedLetters().add(letter); //save player search letter

               if (currentGame.getAttemptsLeft() <= 0) {
                   this.endGame();
               }
           }
        }
        return result;
    }

    public void startNewRound(Game currentGame) {
        // Reset the game with a new cocktail
        currentGame.nextRound();

        Cocktail newCocktail = this.generateCocktail();

        setGameCocktail(newCocktail);
        currentGame.setSelectedLetters(new ArrayList<>());
        currentGame.setCocktailOpenInfo(new HashMap<>());
        currentGame.setAttemptsLeft(MAX_ATTEMPTS); // Reset attempts for the new cocktail


    }

    public void skipRound() {
        this.getLastGame().setAttemptsLeft(this.getLastGame().getAttemptsLeft() - 1);

        if(this.getLastGame().getRound() == MAX_ROUNDS) {
            this.endGame();
        } else {
            this.startNewRound(this.getLastGame());
        }


    }

    public void endGame() {
        Game currentGame = this.getLastGame();
        currentGame.getPlayer().setScore(currentGame.getScore());
        currentGame.endGame();
    }

    public void revealNextLetter() {
        List<String> popularLetters = Arrays.asList("e", "a", "r", "i", "o", "t", "n", "s", "l", "c");
        //todo: add random
        Game currentGame = this.getLastGame();
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

                currentGame.setAttemptsLeft(currentGame.getAttemptsLeft() - 1);

                // Break after revealing the first unselected letter
                break;
            }
        }

        if (currentGame.getAttemptsLeft() <= 0) {
            this.endGame();
        }
    }


    /**
     * Method to show Cocktail data as hints
     * @param cocktail
     * @param property - Cocktail object property for showing as hint
     * @return
     */
    public boolean showCocktailHintInfo(Cocktail cocktail, ApiKeyStr property) {

        Map<String, Object> cocktailMap = new HashMap<>();
        Object obj = null;
        switch (property) {
            case CATEGORY:
                obj = cocktail.getStrCategory();

                break;
            case INGREDIENT:
                obj = cocktail.getIngredientsList();
                break;
            case GLASS:
                obj = cocktail.getStrGlass();
            default:
        }
        String key = property.getValue();
        cocktailMap.put(key, obj);
        if(obj != null) {

            this.getLastGame().getCocktailOpenInfo().put(key, obj);
            //decrease attempts amount
            this.getLastGame().setAttemptsLeft(this.getLastGame().getAttemptsLeft() - 1);
            if (this.getLastGame().getAttemptsLeft() <= 0) {
                this.endGame();
            }
            return true;
        }


        return false;
    }




//    /**
//     * Check if all cocktail hint info already shown or not
//     * @param keys
//     * @return
//     */
//    public boolean mapContainsKeys(List<String> keys) {
//        for (String key : keys) {
//            if (!this.getLastGame().getCocktailOpenInfo().containsKey(key)) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public Game getGameById(int gameId) {
//        Game game = games.get(gameId);
//        if (game == null) {
//            throw new IllegalArgumentException("Game not found for ID: " + gameId);
//        }
//        return game;
//    }


}
