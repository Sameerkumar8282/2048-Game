package com._8.game_service.controller;

import com._8.game_service.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/test")
    public String test() {
        return "Game Service is up!";
    }

    @GetMapping
    public Map<String, Object> getBoard() {
        return gameService.getGameState();
    }

    @PostMapping("/move")
    public Map<String, Object> move(@RequestParam String direction) {
        return gameService.move(direction);
    }

    @PostMapping("/restart")
    public Map<String, Object> restart() {
        gameService.restart();
        return gameService.getGameState();
    }
}
