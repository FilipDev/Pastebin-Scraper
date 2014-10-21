package org.thespherret.pastebinscraper.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class LineReader {

	private LineReader()
	{}

	public static ArrayList<String> readLines(String file)
	{
		try
		{
			return LineReader.readLines(new File(file));
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> readLines(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));

		return readBuffer(reader);
	}

	public static ArrayList<String> readBuffer(BufferedReader reader)
	{
		ArrayList<String> lines = new ArrayList<>();

		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				lines.add(line + "\n");
			}

			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return lines;
	}

	public static String readStringFromBuffer(BufferedReader reader)
	{
		StringBuilder lines = new StringBuilder();

		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				lines.append(line).append("\n");
			}

			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return lines.toString();
	}

}
