package com.example.songlist;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SongController.class)
@Import(DailySongService.class)
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void showsTheDailySongList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Daily Song List")))
                .andExpect(content().string(containsString("<ol>")))
                .andExpect(content().string(containsString("<select id=\"genre\" name=\"genre\">")));
    }

    @Test
    void showsTheSelectedGenre() throws Exception {
        mockMvc.perform(get("/").param("genre", "Pop"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ten songs selected for")))
                .andExpect(content().string(containsString(" in Pop.")))
                .andExpect(content().string(containsString("<option value=\"Pop\" selected>Pop</option>")));
    }
}
