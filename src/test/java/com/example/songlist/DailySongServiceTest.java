package com.example.songlist;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DailySongServiceTest {

    private final DailySongService service = new DailySongService();

    @Test
    void returnsTenSongsForADay() {
        assertThat(service.songsFor(LocalDate.of(2026, 6, 22))).hasSize(10);
    }

    @Test
    void returnsStableSongsForTheSameDay() {
        LocalDate date = LocalDate.of(2026, 6, 22);

        assertThat(service.songsFor(date)).isEqualTo(service.songsFor(date));
    }
}
