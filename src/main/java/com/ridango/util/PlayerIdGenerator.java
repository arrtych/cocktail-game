package com.ridango.util;

public class PlayerIdGenerator {

    private static PlayerIdGenerator instance;
    private int currentId = 0;

    // constructor to prevent instantiation
    private PlayerIdGenerator() {}

    // Method to get the singleton instance of PlayerIdGenerator
    public static synchronized PlayerIdGenerator getInstance() {
        if (instance == null) {
            instance = new PlayerIdGenerator();
        }
        return instance;
    }


    public synchronized int generateId() {
        return ++currentId;
    }
}
