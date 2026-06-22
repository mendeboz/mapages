package com.example.songlist.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.example.songlist.DailySongService;
import com.example.songlist.Song;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DailySongsSteps {

    private final DailySongService service = new DailySongService();
    private LocalDate date;
    private List<Song> songs;

    @Given("the date is {word}")
    public void theDateIs(String value) {
        date = LocalDate.parse(value);
    }

    @When("I ask for the daily songs")
    public void iAskForTheDailySongs() {
        songs = service.songsFor(date);
    }

    @Then("I should receive {int} songs")
    public void iShouldReceiveSongs(int expectedCount) {
        assertThat(songs).hasSize(expectedCount);
    }

    @Then("the same date should always return the same songs")
    public void theSameDateShouldAlwaysReturnTheSameSongs() {
        assertThat(service.songsFor(date)).isEqualTo(songs);
    }
}