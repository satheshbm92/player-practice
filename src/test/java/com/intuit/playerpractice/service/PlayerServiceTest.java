package com.intuit.playerpractice.service;

import com.intuit.playerpractice.exception.PlayerNotFoundException;
import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void testGetAllPlayers() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(new Player(), new Player()));
        List<Player> players = playerService.getAllPlayers();
        assertThat(players).hasSize(2);
    }

    @Test
    public void testGetPlayerByPlayerIdFound() {
        String playerId = "1";
        Player player = new Player();
        player.setPlayerId(playerId);
        when(playerRepository.findByPlayerId(playerId)).thenReturn(Optional.of(player));

        Player found = playerService.getPlayerByPlayerId(playerId);
        assertThat(found).isNotNull();
        assertThat(found.getPlayerId()).isEqualTo(playerId);
    }

    @Test
    public void testGetPlayerByPlayerIdNotFound() {
        String playerId = "unknown";
        when(playerRepository.findByPlayerId(playerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.getPlayerByPlayerId(playerId))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessageContaining("Player not found with playerId: " + playerId);
    }
}
