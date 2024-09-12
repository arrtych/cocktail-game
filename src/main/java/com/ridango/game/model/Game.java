package com.ridango.game.model;

import com.ridango.game.util.GameIdGenerator;

import java.util.List;

public class Game {
    private int id;
    private Player player;

    private int score = 0;

    private int step = 0;

    private Cocktail cocktail;

    private List<char []> wordToGuess;

    public Game(Player player) {
        this.player = player;
    }

    public Game() {
        this.id = GameIdGenerator.getInstance().generateId();
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

    public int getStep() {
        return step;
    }

    public void nextStep() {
        this.step++;
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

    public List<char[]> getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(List<char[]> wordToGuess) {
        this.wordToGuess = wordToGuess;
    }
}
