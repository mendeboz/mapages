package com.example.songlist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class DailySongService {

    private static final List<Song> CATALOG = List.of(
            new Song("Here Comes the Sun", "The Beatles", "Rock"),
            new Song("September", "Earth, Wind & Fire", "Funk"),
            new Song("Dreams", "Fleetwood Mac", "Rock"),
            new Song("Superstition", "Stevie Wonder", "Funk"),
            new Song("Billie Jean", "Michael Jackson", "Pop"),
            new Song("Heroes", "David Bowie", "Rock"),
            new Song("Fast Car", "Tracy Chapman", "Folk"),
            new Song("Dancing Queen", "ABBA", "Pop"),
            new Song("Take on Me", "a-ha", "Pop"),
            new Song("Lovely Day", "Bill Withers", "Soul"),
            new Song("Mr. Brightside", "The Killers", "Indie"),
            new Song("Dog Days Are Over", "Florence + The Machine", "Indie"),
            new Song("Electric Feel", "MGMT", "Indie"),
            new Song("Rebellion (Lies)", "Arcade Fire", "Indie"),
            new Song("Midnight City", "M83", "Electronic"),
            new Song("Good as Hell", "Lizzo", "Pop"),
            new Song("Blinding Lights", "The Weeknd", "Pop"),
            new Song("Levitating", "Dua Lipa", "Pop"),
            new Song("Bad Habit", "Steve Lacy", "R&B"),
            new Song("Texas Sun", "Khruangbin & Leon Bridges", "Soul")
    );

    public List<Song> songsFor(LocalDate date) {
        return songsFor(date, Optional.empty());
    }

    public List<Song> songsFor(LocalDate date, Optional<String> genre) {
        List<Song> songs = CATALOG.stream()
                .filter(song -> genre.isEmpty() || matchesGenre(song, genre.get()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        Collections.shuffle(songs, new Random(date.toEpochDay() + genreSeed(genre)));
        return songs.stream().limit(10).toList();
    }

    public List<String> genres() {
        return CATALOG.stream()
                .map(Song::genre)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private static boolean matchesGenre(Song song, String genre) {
        return song.genre().equalsIgnoreCase(genre.trim());
    }

    private static long genreSeed(Optional<String> genre) {
        return genre.map(value -> value.trim().toLowerCase(Locale.ROOT).hashCode()).orElse(0);
    }
}
