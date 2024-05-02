package com.intuit.playerpractice.controller;

import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@Tag(name = "Player Management System", description = "API for managing players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "View a list of available players")
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @Operation(summary = "Get a player by Player ID")
    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayerByPlayerId(@PathVariable String playerId) {
        Player player = playerService.getPlayerByPlayerId(playerId);
        return ResponseEntity.ok(player);
    }

    @Operation(summary = "Get list of  player by Player prefix")
    @GetMapping("/search")
    public ResponseEntity<List<Player>> getPlayersByFirstNamePrefix(@RequestParam String prefix) {
        List<Player> players = playerService.getPlayersByFirstNamePrefix(prefix);
        return ResponseEntity.ok(players);
    }
}
