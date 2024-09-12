package com.ridango.game.model;

import com.ridango.game.util.PlayerIdGenerator;

public class Player {
    private int id;
    private String name;



    public Player(String name) {
        this.id = PlayerIdGenerator.getInstance().generateId();
        this.name = name;

    }
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
