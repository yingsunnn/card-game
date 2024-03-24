package pro.yingsun.game.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@ActiveProfiles("integration")
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void postCreateGame_200_gameCreatedWithOneDeck() throws Exception {

    this.mockMvc.perform(post("/games")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
                "shoeSize":1
            }
            """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gameId").value(notNullValue()))
        .andExpect(jsonPath("$.players").isEmpty())
        .andExpect(jsonPath("$.shoe", hasSize(52)));
  }
}