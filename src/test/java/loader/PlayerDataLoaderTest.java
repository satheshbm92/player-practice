package loader;


import com.intuit.playerpractice.loader.PlayerDataLoader;
import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerDataLoaderTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private Resource resource;

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private PlayerDataLoader playerDataLoader;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(playerDataLoader, "csvFilePath", "/test-players.csv");
    }

    @Test
    void run_shouldLoadNewPlayersFromCsv() {
        // Arrange
        Set<String> existingPlayerIds = new HashSet<>(List.of("1", "2"));
        when(playerRepository.findAllPlayerIds()).thenReturn(existingPlayerIds);

        // Act
        playerDataLoader.run();

        // Assert
        verify(playerRepository, times(1)).findAllPlayerIds();
        verify(playerRepository, times(1)).saveAll(anyList());
    }

    @Test
    void run_shouldNotSavePlayersWhenNoNewPlayersFound() {
        // Arrange
        Set<String> existingPlayerIds = new HashSet<>(List.of("1", "2", "3"));
        when(playerRepository.findAllPlayerIds()).thenReturn(existingPlayerIds);

        // Act
        playerDataLoader.run();

        // Assert
        verify(playerRepository, times(1)).findAllPlayerIds();
        verify(playerRepository, times(1)).saveAll(new ArrayList<>());
    }

}
