package co.hishab.devtest.controller;

import co.hishab.devtest.model.dto.CreatePlayerRequest;
import co.hishab.devtest.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "players")
@RestController
public class PlayerController {
    private final PlayerService playerService;

    @Operation(summary = "Create a player")
    @PostMapping
    public ResponseEntity<Void> createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        long playerId = playerService.createPlayer(request);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerId).toUri();
        return ResponseEntity.created(location).build();
    }
}
