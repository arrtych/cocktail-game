package com.ridango.game.dto;

import java.util.List;

public class DTO<T> {
    public String status;
    private List<T> drinks;

    public List<T> getList() {
        return drinks;
    }

    public void setList(List<T> list) {
        this.drinks = list;
    }

}
