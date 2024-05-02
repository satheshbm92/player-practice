package com.intuit.playerpractice.service;

import com.intuit.playerpractice.exception.PlayerNotFoundException;
import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerByPlayerId(String playerId) {
        return playerRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with playerId: " + playerId));
    }

    public List<Player> getPlayersByFirstNamePrefix(String prefix) {
        return playerRepository.findByFirstNameStartingWith(prefix);
    }
}
