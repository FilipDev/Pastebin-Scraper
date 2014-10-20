package org.thespherret.pastebinscraper;

import org.thespherret.pastebinscraper.scraper.Scraper;

import java.io.File;
import java.util.Arrays;

public class Main {

	public static void main(String[] args)
	{
		System.out.println("Started scraper.");

		Scraper scraper = new Scraper(true, new File(args[0]), Arrays.copyOfRange(args, 1, args.length - 1));
		scraper.start();
		scraper.run();
	}

}
