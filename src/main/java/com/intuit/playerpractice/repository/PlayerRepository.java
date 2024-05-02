package com.intuit.playerpractice.repository;

import com.intuit.playerpractice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByPlayerId(String playerId);

    List<Player> findByFirstNameStartingWith(String prefix);
}
