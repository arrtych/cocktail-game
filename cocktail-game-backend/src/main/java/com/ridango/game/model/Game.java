package com.ridango.game.model;

import com.ridango.game.util.GameIdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private int id;
    private Player player;

    private int score = 0;

    private int attemptsLeft = 5; // Max attempts per game

    /**
     * Current cocktail for guess
     */
    private Cocktail cocktail;

    /**
     * Cocktails that were shown in the game, that player tried to guess
     */
    private List<Cocktail> gameCocktails = new ArrayList<>();

    /**
     * Cocktails that were generated for game
     */
    private List<Cocktail> cocktailDB = new ArrayList<>();

    private int round;


    private List<String> wordToGuess;

    private List<String> playerGuess;

    private List<String> selectedLetters = new ArrayList<>();

    /**
     * opened cocktail data, when player used hints
     */
    private Map<String, Object> cocktailOpenInfo = new HashMap<>();


    private boolean isActive;

    public Game(Player player) {
        this.player = player;
    }

    public Game() {
        this.id = GameIdGenerator.getInstance().generateId();
        this.isActive = true;
        this.round = 1;
    }

    public int getId() {
        return id;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public Player getPlayer() {
        return player;
    }


    public void setPlayer(Player player) {
        //todo: check if player already exists
        //add exception if no player
        this.player = player;

    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public List<String> getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(List<String> wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public boolean isActive() {
        return isActive;
    }

    public void endGame() {
        isActive = false;
    }

    public List<String> getPlayerGuess() {
        return playerGuess;
    }

    public void setPlayerGuess(List<String> playerGuess) {
        this.playerGuess = playerGuess;
    }

    public List<String> getSelectedLetters() {
        return selectedLetters;
    }

    public void setSelectedLetters(List<String> selectedLetters) {
        this.selectedLetters = selectedLetters;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public Map<String, Object> getCocktailOpenInfo() {
        return cocktailOpenInfo;
    }

    public void setCocktailOpenInfo(Map<String, Object> cocktailOpenInfo) {
        this.cocktailOpenInfo = cocktailOpenInfo;
    }

    public List<Cocktail> getGameCocktails() {
        return gameCocktails;
    }

    public void setGameCocktails(List<Cocktail> gameCocktails) {
        this.gameCocktails = gameCocktails;
    }

    public List<Cocktail> getCocktailDB() {
        return cocktailDB;
    }

    public void setCocktailDB(List<Cocktail> cocktailDB) {
        this.cocktailDB = cocktailDB;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void nextRound() {
        this.round++;
    }
}
