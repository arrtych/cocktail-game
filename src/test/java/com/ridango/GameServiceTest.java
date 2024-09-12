package com.ridango;

import com.ridango.game.model.Game;
import com.ridango.game.service.GameService;
import com.ridango.game.service.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameServiceTest {

    private RestClient api;
    private Game game;

    private GameService gameService;
    @BeforeEach
    public void init() {
//        api = new RestClient();
//        game = new Game();
        gameService = new GameService();

    }

    @Test
//    public void generateCocktailTest() {
    public void startGameTest() {
        this.gameService.startGame();
    }


    @Test
    public void printWordTest() {
        this.gameService.startGame();
        this.gameService.printWord();
    }
}
