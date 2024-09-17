package com.ridango.game.types;

public enum ApiKeyStr {
    ID("idDrink"),
    CATEGORY("strCategory"),

    DRINK("strDrink"),
    ALCOHOLIC("strAlcoholic"),

    INGREDIENT("strIngredient"),

    GLASS("strGlass"),

    INSTRUCTIONS("strInstructions");


    private String value;
    ApiKeyStr(String strCategory) {
        this.value = strCategory;
    }

    public String getValue() {
        return value;
    }
}
