package com.ridango;

import com.ridango.model.Cocktail;
import com.ridango.model.Game;
import com.ridango.model.Player;
import com.ridango.service.RestClient;
import com.ridango.types.ApiKeyStr;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    private RestClient api;

    private Player player;

    private final int drinkTypeAmount = 11;

    private final int totalCocktailsAmount = 441;


    @BeforeEach
    public void init() {
        api = new RestClient();
        Game game = new Game(player);
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

    }

    @Test
    public void ApiGetDrinkTypesTest() {
        api.getAllDrinkTypes();
        assertEquals(drinkTypeAmount, api.getAllDrinkTypes().getList().size());
//        System.out.println(api.getAllDrinkTypes().getList());
    }

    @Test
    public void ApiGetAllCocktailsByName() {
        List<Cocktail> cocktails = api.getAllCocktailsByName("margarita").getList();
        assertEquals(6, cocktails.size());
    }

    @Test
    public void ApiGetCocktailsByFirstLetter() {
        List<Cocktail> cocktails = api.getAllCocktailsByFirstLetter("a").getList();
        assertEquals(25, api.getAllCocktailsByFirstLetter("a").getList().size());
//        Cocktail cocktail = cocktails.stream().
//                filter(p -> p.getStrDrink().equals("ABC")).
//                findAny().orElse(null);
//
//        Cocktail cocktail2 = cocktails.stream().
//                filter(p -> p.getStrDrink().equals("Afterglow")).
//                findAny().orElse(null);
//        assertEquals(true, cocktail.isAlcoholic());
//        assertEquals(false, cocktail2.isAlcoholic());
    }


    @Test
    public void getAllCocktailsFromDB() {
        List<Cocktail> list = api.getAllCocktailsFromDB();
        assertEquals(totalCocktailsAmount, list.size());


    }
}
