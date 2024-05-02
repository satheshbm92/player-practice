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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDataLoader.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Value("${player.data.csv.path:/players.csv}")
    private String csvFilePath;

    @Override
    @Transactional
    public void run(String... args) {
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(csvFilePath))) {
            CsvToBean<Player> csvToBean = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            var newPlayers = csvToBean.parse().stream().filter(player ->
                    playerRepository.findByPlayerId(player.getPlayerId()).isEmpty()
            ).collect(Collectors.toList());

            playerRepository.saveAll(newPlayers);
            logger.info("Loaded {} new players into the database.", newPlayers.size());
        } catch (IOException e) {
            logger.error("Error occurred while loading player data from CSV.", e);
        }
    }
}
