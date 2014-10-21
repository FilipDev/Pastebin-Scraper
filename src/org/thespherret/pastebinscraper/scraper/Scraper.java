package org.thespherret.pastebinscraper.scraper;

import org.thespherret.pastebinscraper.files.LineReader;
import org.thespherret.pastebinscraper.files.LineWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scraper {

	private boolean saveAll = true;
	private boolean silent = false;
	private boolean run;

	private File outputDirectory;

	private String alertStrings[] = {};

	public Scraper(File directory)
	{
		this.outputDirectory = directory;
	}

	public Scraper(boolean silent, File directory, String... alertStrings)
	{
		this.outputDirectory = directory;
		this.alertStrings = alertStrings;
		this.silent = silent;
	}

	public Scraper(boolean silent, File directory, boolean saveAll, String... alertStrings)
	{
		this.outputDirectory = directory;
		this.alertStrings = alertStrings;
		this.saveAll = saveAll;
		this.silent = silent;
	}

	public void start()
	{
		this.run = true;
	}

	public void stop()
	{
		this.run = false;
	}

	public Set<String> pastes = new HashSet<>();

	public Set<Paste> run()
	{
		while (true)
		{
			try
			{
				if (run)
				{
					for (final Paste paste : scrape())
					{
						if (!pastes.contains(paste.getId()))
						{
							pastes.add(paste.getId());

							File outputFile = new File(outputDirectory.getAbsolutePath() + File.separator + paste.getName() + "-" + paste.getId() + ".txt");

							if (outputFile.exists())
								continue;

							System.out.println("======================================================");
							System.out.println(paste.getId() + ":");
							System.out.println(paste.getText());

							boolean alerted = false;

							if (alertStrings.length != 0)
							{
								for (String alertString : alertStrings)
								{
									if (paste.getText().contains(alertString))
									{
										final JFrame alert = new JFrame("Paste with term " + alertString + " found.");

										alert.setLayout(new GridLayout());

										JTextArea textArea = new JTextArea("Found term \"" + alertString + "\" in the paste " + paste.getName() + "\n");
										textArea.append("Link to paste: " + paste.getUrl() + "\n\n\n");
										int indexOfKeyword = paste.getText().indexOf(alertString);
										textArea.append("Preview: \n" + paste.getText().substring((indexOfKeyword - 40) < 0 ? 0 : (indexOfKeyword - 40), (indexOfKeyword + 40) > paste.getText().length() ? 0 : (indexOfKeyword + 40)));
										JButton copyButton = new JButton("Click to open URL.");

										copyButton.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e)
											{
												try
												{
													Desktop.getDesktop().browse(new URI(paste.getUrl()));
													alert.dispose();
												} catch (IOException | URISyntaxException e1)
												{
													e1.printStackTrace();
												}
											}
										});
										textArea.setEditable(false);
										alert.getContentPane().add(textArea);
										alert.getContentPane().add(copyButton);
										alert.setSize(900, 450);
										alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

										if (!this.silent)
										{
											alert.getToolkit().beep();
											alert.show();
										}
										else
										{
											alert.setVisible(true);
											alert.setState(Frame.ICONIFIED);
										}

										alerted = true;
										break;
									}
								}
							}

							if (alerted || saveAll)
								LineWriter.writeString(outputFile, paste.getText());
						}
					}
				}
				Thread.sleep(180 * 1000L);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public Set<Paste> scrape()
	{
		Set<Paste> pastes = new HashSet<>();
		try
		{
			URL archive = new URL("http://pastebin.com/archive");
			List<String> lines = LineReader.readBuffer(new BufferedReader(new InputStreamReader(archive.openConnection().getInputStream())));

			lines = lines.subList(lines.indexOf("\t\t<table class=\"maintable\" cellspacing=\"0\">\n") + 5, lines.indexOf("\t\t</table>\n"));

			for (String line : lines)
			{
				if (line.contains("a href=\"/") && !line.startsWith("\t\t\t\t<td align=\"right\">"))
				{
					int endTag = line.lastIndexOf("</a>");
					if (endTag == -1)
						endTag = line.length() - 1;
					Paste paste = new Paste(line.substring(line.lastIndexOf("<a href=\"/") + 10, endTag));

					pastes.add(paste);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return pastes;

	}

}
