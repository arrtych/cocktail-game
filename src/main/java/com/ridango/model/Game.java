package com.ridango.model;

import com.ridango.util.GameIdGenerator;

public class Game {
    private int id;
    private Player player;

    private int score = 0;

    private int step = 0;


    private Cocktail cocktail;

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
}
