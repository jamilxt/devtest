package co.hishab.devtest.controller;

import co.hishab.devtest.model.dto.GameStartResponse;
import co.hishab.devtest.model.dto.Scoreboard;
import co.hishab.devtest.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "games")
@RestController
public class GameController {
    public static final String GAME_STARTED_RESPONSE = "Please check the console log to get the game update";
    private final GameService gameService;

    @Operation(summary = "Start the game")
    @PostMapping("start")
    public ResponseEntity<GameStartResponse> startGame() {
        gameService.validateStartGame();
        gameService.startGame();
        var response = new GameStartResponse(GAME_STARTED_RESPONSE);
        return ResponseEntity.accepted().body(response);
    }

    @Operation(summary = "Game Scoreboard")
    @PostMapping("scoreboard")
    public ResponseEntity<Scoreboard> getGameScoreboard() {
        return ResponseEntity.ok(gameService.getGameScoreboard());
    }
}
