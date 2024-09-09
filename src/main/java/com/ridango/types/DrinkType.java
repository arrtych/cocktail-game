package com.ridango.types;

public class DrinkType {
    private String strCategory;
    private int id;

    public DrinkType(String strCategory) {
        this.strCategory = strCategory;
    }


    @Override
    public String toString() {
        return "DrinkType {"
                + "category name='" + strCategory + '\''
                + '}';
    }

}
