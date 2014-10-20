package org.thespherret.pastebinscraper.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class LineWriter {

	private LineWriter()
	{}

	public static boolean writeLines(File file, List<String> lines)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for (String line : lines)
			{
				writer.write(line + System.lineSeparator());
			}

			writer.flush();
			writer.close();
			return true;
		} catch (IOException e)
		{
			return false;
		}
	}

}
