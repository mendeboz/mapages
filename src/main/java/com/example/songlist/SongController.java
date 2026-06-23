package com.example.songlist;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    private final DailySongService dailySongService;

    public SongController(DailySongService dailySongService) {
        this.dailySongService = dailySongService;
    }

    @GetMapping("/")
    public String index(@RequestParam Optional<String> genre) {
        LocalDate today = LocalDate.now();
        List<String> genres = dailySongService.genres();
        Optional<String> selectedGenre = selectedGenre(genre, genres);
        List<Song> songs = dailySongService.songsFor(today, selectedGenre);

        StringBuilder html = new StringBuilder();
        html.append("""
                <!doctype html>
                <html lang="en">
                  <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>Daily Song List</title>
                    <style>
                      body { margin: 0; min-height: 100vh; display: grid; place-items: center; padding: 24px; font-family: Arial, sans-serif; color: #18212f; background: #eef3f8; }
                      main { width: min(760px, 100%); padding: 40px; border: 1px solid #ced8e4; border-radius: 8px; background: #fff; box-shadow: 0 20px 50px rgba(24, 33, 47, .1); }
                      h1 { margin: 0 0 8px; font-size: clamp(2rem, 5vw, 3.25rem); }
                      p { margin: 0 0 28px; color: #596779; }
                      form { display: flex; flex-wrap: wrap; gap: 10px 14px; align-items: center; margin: 0 0 24px; }
                      label { font-weight: 700; }
                      select, button { min-height: 40px; border: 1px solid #b9c6d6; border-radius: 6px; background: #fff; color: #18212f; font: inherit; }
                      select { min-width: 180px; padding: 0 34px 0 12px; }
                      button { padding: 0 16px; font-weight: 700; cursor: pointer; }
                      ol { margin: 0; padding-left: 24px; }
                      li { padding: 10px 0; border-top: 1px solid #e5ebf2; }
                      strong { display: inline-block; min-width: 16ch; }
                      .genre { display: inline-block; margin-left: 8px; padding: 2px 8px; border-radius: 999px; background: #e9f6f3; color: #0f766e; font-size: .85rem; font-weight: 700; }
                    </style>
                  </head>
                  <body>
                    <main>
                """);
        html.append("<h1>Daily Song List</h1>");
        html.append("<p>Ten songs selected for ").append(today);
        selectedGenre.ifPresent(value -> html.append(" in ").append(escape(value)));
        html.append(".</p>");
        appendGenreForm(html, genres, selectedGenre);
        html.append("<ol>");
        for (Song song : songs) {
            html.append("<li><strong>")
                    .append(escape(song.title()))
                    .append("</strong> ")
                    .append(escape(song.artist()))
                    .append(" <span class=\"genre\">")
                    .append(escape(song.genre()))
                    .append("</span></li>");
        }
        html.append("""
                    </ol>
                  </main>
                </body>
                </html>
                """);
        return html.toString();
    }

    private static void appendGenreForm(StringBuilder html, List<String> genres, Optional<String> selectedGenre) {
        html.append("<form method=\"get\"><label for=\"genre\">Genre</label><select id=\"genre\" name=\"genre\">");
        html.append("<option value=\"\"");
        if (selectedGenre.isEmpty()) {
            html.append(" selected");
        }
        html.append(">All genres</option>");
        for (String genre : genres) {
            html.append("<option value=\"")
                    .append(escape(genre))
                    .append("\"");
            if (selectedGenre.filter(genre::equals).isPresent()) {
                html.append(" selected");
            }
            html.append(">")
                    .append(escape(genre))
                    .append("</option>");
        }
        html.append("</select><button type=\"submit\">Choose</button></form>");
    }

    private static Optional<String> selectedGenre(Optional<String> requestedGenre, List<String> genres) {
        return requestedGenre
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .flatMap(value -> genres.stream()
                        .filter(genre -> genre.equalsIgnoreCase(value))
                        .findFirst());
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
