package com.example.songlist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class DailySongService {

    private static final List<Song> CATALOG = List.of(
            new Song("Here Comes the Sun", "The Beatles"),
            new Song("September", "Earth, Wind & Fire"),
            new Song("Dreams", "Fleetwood Mac"),
            new Song("Superstition", "Stevie Wonder"),
            new Song("Billie Jean", "Michael Jackson"),
            new Song("Heroes", "David Bowie"),
            new Song("Fast Car", "Tracy Chapman"),
            new Song("Dancing Queen", "ABBA"),
            new Song("Take on Me", "a-ha"),
            new Song("Lovely Day", "Bill Withers"),
            new Song("Mr. Brightside", "The Killers"),
            new Song("Dog Days Are Over", "Florence + The Machine"),
            new Song("Electric Feel", "MGMT"),
            new Song("Rebellion (Lies)", "Arcade Fire"),
            new Song("Midnight City", "M83"),
            new Song("Good as Hell", "Lizzo"),
            new Song("Blinding Lights", "The Weeknd"),
            new Song("Levitating", "Dua Lipa"),
            new Song("Bad Habit", "Steve Lacy"),
            new Song("Texas Sun", "Khruangbin & Leon Bridges")
    );

    public List<Song> songsFor(LocalDate date) {
        List<Song> songs = new ArrayList<>(CATALOG);
        Collections.shuffle(songs, new Random(date.toEpochDay()));
        return songs.stream().limit(10).toList();
    }
}
