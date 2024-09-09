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

    private RestClient response;

    private Player player;

    private final int drinkTypeAmount = 11;


    @BeforeEach
    public void init() {
        response = new RestClient();
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
        response.getAllDrinkTypes();
        assertEquals(drinkTypeAmount, response.getAllDrinkTypes().getList().size());
        System.out.println(response.getAllDrinkTypes().getList());
    }

    @Test
    public void getAllCocktailsByName() {
        List<Cocktail> cocktails = response.getAllCocktailsByName("margarita").getList();
        assertEquals(6, cocktails.size());
    }

}
