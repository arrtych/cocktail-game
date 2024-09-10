package com.ridango.util;

public class GameIdGenerator {

    private static GameIdGenerator instance;
    private int currentId = 0;

    private GameIdGenerator() {
    }

    public static synchronized GameIdGenerator getInstance() {
        if (instance == null) {
            instance = new GameIdGenerator();
        }
        return instance;
    }

    public synchronized int generateId() {
        return ++currentId;
    }
}