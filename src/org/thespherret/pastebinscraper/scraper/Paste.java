package org.thespherret.pastebinscraper.scraper;

import org.thespherret.pastebinscraper.files.LineReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Paste {

    private String url;
    private String name;
    private String id;

    private String text;

    public Paste(String paste) {
        String[] pasteParts = paste.split("\">");
        this.url = "http://www.pastebin.com/" + pasteParts[0];
        this.id = pasteParts[0];
        this.name = pasteParts.length != 2 ? "Untitled" : pasteParts[1];
        try {
            this.text = LineReader.readStringFromBuffer(new BufferedReader(new InputStreamReader(new URL("http://pastebin.com/download.php?i=" + this.id).openConnection().getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

}
