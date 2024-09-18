package com.ridango.game.controller;

import com.ridango.game.dto.PlayerNameRequest;
import com.ridango.game.exceptions.GameOverException;
import com.ridango.game.model.Cocktail;
import com.ridango.game.model.Game;
import com.ridango.game.model.Player;
import com.ridango.game.service.GameService;
import com.ridango.game.types.ApiKeyStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    public GameController(GameService service) {
        this.gameService = service;
    }

    // Start a new game for a player
    @PostMapping("/start")
    public Game startGame(@RequestBody PlayerNameRequest request) {
        return gameService.startNewGame(request.getPlayerName());
    }


    @PutMapping("/{playerId}/guess")
    public ResponseEntity<Game> guessLetter(@PathVariable int playerId, @RequestParam String letter) {
//        try {
          gameService.checkPlayerGuess(letter, playerId);
            //todo: think about 500
//            return ResponseEntity.ok(correctGuess ? "Correct guess!" : "Incorrect guess. Try again.");
            return ResponseEntity.ok(gameService.getLastGame());
//        } catch (GameOverException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
    }

    // Get the current state of the game
    @GetMapping("/state")
    public ResponseEntity<Game> getCurrentGameState() {
        return ResponseEntity.ok(gameService.getLastGame()); // You might want to handle cases where no game exists for the player
    }


    @PutMapping("/skip")
    public ResponseEntity<Game> skipRound() {
        this.gameService.skipRound();
        return ResponseEntity.ok(gameService.getLastGame());

    }

    @PutMapping("reveal-letter")
    public ResponseEntity<Game> revealNextLetter() {
        this.gameService.revealNextLetter();
        return ResponseEntity.ok(gameService.getLastGame());
    }

    @PutMapping("show-cocktail-hints/{cocktailParam}")
    public ResponseEntity<Game> showCocktailHintInfo(@PathVariable String cocktailParam) {
        Cocktail cocktail = this.gameService.getLastGame().getCocktail();
        ApiKeyStr param = getCocktailParam(cocktail,cocktailParam);
        this.gameService.showCocktailHintInfo(cocktail, param);
        return ResponseEntity.ok(gameService.getLastGame());
    }



    private ApiKeyStr getCocktailParam(Cocktail cocktail, String param) {
        ApiKeyStr value = null;
        switch (param) {
            case "category":
                value = ApiKeyStr.CATEGORY;

                break;
            case "ingredients":
                value = ApiKeyStr.INGREDIENT;
                break;
            case "glass":
                value = ApiKeyStr.GLASS;
            default:
        }
        return value;
    }

    @GetMapping("/all-games")
    public ResponseEntity<Map<Integer, Game>> getAllGames() {
        return ResponseEntity.ok(gameService.getGames());
    }


    @PutMapping("/finish-game")
    public ResponseEntity<Game> finishGame() {
        gameService.endGame();
        return ResponseEntity.ok(gameService.getLastGame());
    }




}
