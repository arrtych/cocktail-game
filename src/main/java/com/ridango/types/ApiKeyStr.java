package com.ridango.types;

public enum ApiKeyStr {
    ID("idDrink"),
    CATEGORY("strCategory"),

    DRINK("strDrink"),
    ALCOHOLIC("strAlcoholic"),

    INGREDIENT("strIngredient");


    private String value;
    ApiKeyStr(String strCategory) {
        this.value = strCategory;
    }

    public String getValue() {
        return value;
    }
}
