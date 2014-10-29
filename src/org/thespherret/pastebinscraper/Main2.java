package org.thespherret.pastebinscraper;

import org.thespherret.pastebinscraper.scraper.Scraper;

import java.io.File;

public class Main2 {

    public static void main(String[] args) {
        Scraper scraper = new Scraper(true, new File("C:\\Users\\Administrator\\Desktop\\New Test Folder"), ":password", ":minecraft");
        scraper.start();
        scraper.run();
    }

}
