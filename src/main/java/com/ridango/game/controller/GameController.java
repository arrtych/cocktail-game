package com.ridango.game.controller;

import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    public GameController(GameService service) {
        this.gameService = service;
    }

    // Start a new game for a player
    @PostMapping("/start")
    public Game startGame(@RequestBody Player player) {
        return gameService.startNewGame(player);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Spring Boot Application!2";
    }
}
