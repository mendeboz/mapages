package com.example.songlist;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    private final DailySongService dailySongService;

    public SongController(DailySongService dailySongService) {
        this.dailySongService = dailySongService;
    }

    @GetMapping("/")
    public String index() {
        LocalDate today = LocalDate.now();
        List<Song> songs = dailySongService.songsFor(today);

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
                      ol { margin: 0; padding-left: 24px; }
                      li { padding: 10px 0; border-top: 1px solid #e5ebf2; }
                      strong { display: inline-block; min-width: 16ch; }
                    </style>
                  </head>
                  <body>
                    <main>
                """);
        html.append("<h1>Daily Song List</h1>");
        html.append("<p>Ten songs selected for ").append(today).append(".</p>");
        html.append("<ol>");
        for (Song song : songs) {
            html.append("<li><strong>")
                    .append(escape(song.title()))
                    .append("</strong> ")
                    .append(escape(song.artist()))
                    .append("</li>");
        }
        html.append("""
                    </ol>
                  </main>
                </body>
                </html>
                """);
        return html.toString();
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}