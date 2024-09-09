package com.ridango.model;

public class Game {
    private Player player;

    private int score = 0;

    private int step = 0;

    public Game(Player player) {
        this.player = player;
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


}
