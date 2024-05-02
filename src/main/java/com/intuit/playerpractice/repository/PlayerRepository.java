package com.intuit.playerpractice.repository;

import com.intuit.playerpractice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByPlayerId(String playerId);

    List<Player> findByFirstNameStartingWith(String prefix);

    @Query("SELECT p.playerId FROM Player p")
    Set<String> findAllPlayerIds();
}
