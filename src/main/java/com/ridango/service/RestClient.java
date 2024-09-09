package com.ridango.service;


import com.google.gson.JsonArray;
import com.ridango.dto.DTO;
import com.ridango.exceptions.CocktailsGetException;
import com.ridango.model.Cocktail;
import com.ridango.types.ApiKeyStr;
import com.ridango.types.DrinkType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestClient {
    private String apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/";

    private String drinkTypeList = "list.php?c=list";
    private String apiSearch = "search.php?s=";
    private String apiSearchByFilter = "search.php?f=";

    HttpClient client = HttpClient.newHttpClient();


    private final int ingredientsMaxCount = 15;


    public DTO<DrinkType> getAllDrinkTypes() {
        DTO<DrinkType> obj = null;
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format(apiUrl + drinkTypeList)))
                .GET()
                .build();
        try {
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = null;
            List<DrinkType> drinkTypes = new ArrayList<>();
            jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();
            obj = new Gson().fromJson(response.body(), DTO.class);
            if (obj.getList() != null) {
                JsonArray arr = jsonObject.getAsJsonArray("drinks");
                for (int i = 0; i < arr.size(); i++) {
                    DrinkType dt = new DrinkType(arr.get(i)
                            .getAsJsonObject()
                            .get(ApiKeyStr.CATEGORY.getValue())
                            .getAsString());
                    drinkTypes.add(dt);
                }
                obj.setList(drinkTypes);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
        return obj;

    }


    /**
     * Search cocktail by name.
     *
     * @param name
     * @return
     */
    public DTO<Cocktail> getAllCocktailsByName(String name) {
        HttpClient client = HttpClient.newHttpClient();
        String url = this.apiUrl + this.apiSearch + name;
        DTO<Cocktail> cocktailDTO = new DTO<Cocktail>();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url)))
                .GET()
                .build();
        try {
            cocktailDTO = createCocktailsList(httpRequest, client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cocktailDTO;
    }


    /**
     * Generates cocktail list and returns as DTO<Cocktail> object.
     *
     * @param httpRequest
     * @param client
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public DTO<Cocktail> createCocktailsList(HttpRequest httpRequest, HttpClient client) throws IOException, InterruptedException {
        DTO<Cocktail> cocktailDTO = new DTO<Cocktail>();
        JsonObject jsonObject = null;
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();
        DTO<Cocktail> obj = new Gson().fromJson(response.body(), DTO.class);
        if (obj.getList() != null) {
            JsonArray arr = jsonObject.getAsJsonArray("drinks");
            List<Cocktail> cocktailList = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                Cocktail cocktail = new Cocktail();
                cocktail.setStrDrink(arr.get(i).getAsJsonObject().get(ApiKeyStr.DRINK.getValue()).getAsString()); // help function
                cocktail.setStrCategory(arr.get(i).getAsJsonObject().get(ApiKeyStr.CATEGORY.getValue()).getAsString());
//                cocktail.setStrGlass(arr.get(i).getAsJsonObject().get("strGlass").getAsString());
                cocktail.setIdDrink(arr.get(i).getAsJsonObject().get(ApiKeyStr.ID.getValue()).getAsString());
                if (arr.get(i).getAsJsonObject().get(ApiKeyStr.ALCOHOLIC.getValue()).getAsString().equals("Alcoholic")) {
                    cocktail.setAlcoholic(true);
                } else {
                    cocktail.setAlcoholic(false);
                }
                List<String> ingredientList = new ArrayList<>();
                for (int j = 1; j <= ingredientsMaxCount; j++) {
                    if (!arr.get(i).getAsJsonObject().get(ApiKeyStr.INGREDIENT.getValue() + j).isJsonNull()) {
                        String strIngredient = arr.get(i).getAsJsonObject().get(ApiKeyStr.INGREDIENT.getValue() + j).getAsString();
                        ingredientList.add(strIngredient);
                    }
                }
                cocktail.setIngredientsList(ingredientList);
                cocktailList.add(cocktail);
            }
            cocktailDTO.setList(cocktailList);
        } else {
            throw new CocktailsGetException("Get request failed. No objects.");
        }
        return cocktailDTO;
    }


    /**
     * List of all cocktails by first letter.
     *
     * @param letter
     * @return
     */
    public DTO<Cocktail> getAllCocktailsByFirstLetter(String letter) {
        HttpClient client = HttpClient.newHttpClient();
        String url = this.apiUrl + this.apiSearchByFilter + letter; //
        DTO<Cocktail> cocktailDTO = new DTO<Cocktail>();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url)))
                .GET()
                .build();
        try {
            cocktailDTO = createCocktailsList(httpRequest, client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cocktailDTO;
    }


    /**
     * Get all possible cocktails from API
     *
     * @return
     */
    public List<Cocktail> getAllCocktailsFromDB() {
        String possibleChars = "12345679abcdefghijklmnopqrstvwyz";
        List<Cocktail> cocktailList = new ArrayList<>();
        try {
            for (int i = 0; i < possibleChars.length(); i++) {
                String letter = Character.toString(possibleChars.charAt(i));
                List<Cocktail> cocktails = getAllCocktailsByFirstLetter(letter).getList();
                if (cocktails != null) {
                    for (Cocktail c : cocktails) {
                        cocktailList.add(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cocktailList;
    }


}
