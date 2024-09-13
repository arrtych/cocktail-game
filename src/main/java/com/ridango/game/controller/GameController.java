package com.ridango.game.controller;

import com.ridango.game.exceptions.GameOverException;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{playerId}/guess")
    public ResponseEntity<String> guessLetter(@PathVariable int playerId, @RequestParam String letter) {
        try {
            boolean correctGuess = gameService.checkPlayerGuess(letter, playerId);
            //todo: think about 500
            return ResponseEntity.ok(correctGuess ? "Correct guess!" : "Incorrect guess. Try again.");
        } catch (GameOverException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get the current state of the game
    @GetMapping("/state")
    public ResponseEntity<Game> getCurrentGameState() {

        return ResponseEntity.ok(gameService.getLastGame()); // You might want to handle cases where no game exists for the player
    }

    //todo: endgame()


    @PutMapping("/skip")
    public ResponseEntity<Game> skipRound() {
        this.gameService.skipRound();
        return ResponseEntity.ok(gameService.getLastGame());

    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Spring Boot Application!2";
    }
}
