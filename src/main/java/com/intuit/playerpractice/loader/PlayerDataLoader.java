package com.intuit.playerpractice.loader;


import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.repository.PlayerRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PlayerDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDataLoader.class);

    private final PlayerRepository playerRepository;

    @Value("${player.data.csv.path:/players.csv}")
    public String csvFilePath;

    @Autowired
    public PlayerDataLoader(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(csvFilePath))) {
            CsvToBean<Player> csvToBean = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Player> players = csvToBean.parse();
            List<Player> newPlayers = new ArrayList<>();

            // Fetch all existing player IDs and store them in a set
            Set<String> existingPlayerIds = playerRepository.findAllPlayerIds();

            for (Player player : players) {
                // Check if the player ID exists in the set of existing player IDs
                if (!existingPlayerIds.contains(player.getPlayerId())) {
                    newPlayers.add(player);
                }
            }
            playerRepository.saveAll(newPlayers);
            logger.info("Loaded {} new players into the database.", newPlayers.size());
        }
        catch (IOException e) {
            logger.error("Error occurred while loading player data from CSV.", e);
            throw new RuntimeException("Failed to process the player data file", e);
        }
        catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw e;
        }
    }
}
