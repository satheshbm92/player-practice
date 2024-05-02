package com.intuit.playerpractice.controller;

import com.intuit.playerpractice.exception.PlayerNotFoundException;
import com.intuit.playerpractice.model.Player;
import com.intuit.playerpractice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        // Setup your test data here
    }

//    @Test
//    void getAllPlayers() throws Exception {
//        given(playerService.getAllPlayers()).willReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/players"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"));
//    }

    @Test
    void getPlayerByIdSuccess() throws Exception {
        Player mockPlayer = new Player();
        mockPlayer.setPlayerId("1");
        mockPlayer.setFirstName("Test Player");
        given(playerService.getPlayerByPlayerId("1")).willReturn(mockPlayer);

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test Player"));
    }

    @Test
    void getPlayerByIdNotFound() throws Exception {
        String playerId = "unknown_id";
        given(playerService.getPlayerByPlayerId(playerId)).willThrow(new PlayerNotFoundException("Player not found with playerId: " + playerId));

        mockMvc.perform(get("/api/players/" + playerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Player not found with playerId: " + playerId)));
    }


}