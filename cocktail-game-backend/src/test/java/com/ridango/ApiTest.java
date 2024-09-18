package com.ridango;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Player;
import com.ridango.game.service.RestClient;
import com.ridango.game.types.ApiKeyStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    private RestClient api;

    private Player player;

    private final int drinkTypeAmount = 11;

    private final int totalCocktailsAmount = 441;


    @BeforeEach
    public void init() {
        api = new RestClient();
//        Game game = new Game(player);
    }

    @Test
    public void ApiKeyStrTest() {
        String id = ApiKeyStr.ID.getValue();
        assertEquals("idDrink", id);

        String strCategory = ApiKeyStr.CATEGORY.getValue();
        assertEquals("strCategory", strCategory);

        String strDrink = ApiKeyStr.DRINK.getValue();
        assertEquals("strDrink", strDrink);

        String strAlcoholic = ApiKeyStr.ALCOHOLIC.getValue();
        assertEquals("strAlcoholic", strAlcoholic);

        String strIngredient = ApiKeyStr.INGREDIENT.getValue();
        assertEquals("strIngredient", strIngredient);

        String strInstructions = ApiKeyStr.INSTRUCTIONS.getValue();
        assertEquals("strInstructions", strInstructions);

        String strGlass = ApiKeyStr.GLASS.getValue();
        assertEquals("strGlass", strGlass);


    }

    @Test
    public void ApiGetDrinkTypesTest() {
        api.getAllDrinkTypes();
        assertEquals(drinkTypeAmount, api.getAllDrinkTypes().getList().size());
//        System.out.println(api.getAllDrinkTypes().getList());
    }

    @Test
    public void ApiGetAllCocktailsByNameTest() {
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        assertEquals(6, cocktails.size());
    }

    @Test
    public void ApiGetCocktailsByFirstLetterTest() {
        List<Cocktail> cocktails = api.getAllCocktailsByFirstLetter("a").getList();
        assertEquals(25, api.getAllCocktailsByFirstLetter("a").getList().size());
        Cocktail cocktail = cocktails.stream().
                filter(p -> p.getStrDrink().equals("ABC")).
                findAny().orElse(null);

        Cocktail cocktail2 = cocktails.stream().
                filter(p -> p.getStrDrink().equals("Afterglow")).
                findAny().orElse(null);
        assertEquals(true, cocktail.isAlcoholic());
        assertEquals(false, cocktail2.isAlcoholic());
    }


    @Test
    public void getAllCocktailsFromDBTest() {
        List<Cocktail> list = api.getAllCocktailsFromDB();
        assertEquals(totalCocktailsAmount, list.size());
        assertTrue(list.get(1) instanceof Cocktail);
    }

    @Test
    public void getRandomCocktailsFromDBTest() {
        List<Cocktail> list = api.getRandomCocktailsFromDB(10);
        assertTrue(list.get(9) instanceof Cocktail);
        assertEquals(10, list.size());

        List<Cocktail> list2 = api.getRandomCocktailsFromDB(5);
        assertEquals(5, list2.size());

        List<Cocktail> list3 = api.getRandomCocktailsFromDB(3);
        assertEquals(3, list3.size());
    }
}
