package org.thespherret.pastebinscraper;

import org.thespherret.pastebinscraper.scraper.Scraper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {

	public static void main(String[] args)
	{
		new Main();
	}

	private JPanel panel1;
	private JTextField keywordsTextField;
	private JCheckBox silentModeCheckBox;
	private JCheckBox onlySaveWhenKeywordCheckBox;
	private JTextField destinationTextField;
	private JButton startButton;
	private JButton stopButton;

	private Scraper scraper;

	public Main()
	{
        super("Pastebin Scraper");
		setContentPane(panel1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		show();
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (scraper != null)
				{
					if (scraper.isScraping())
						return;

					if (scraper.isRunning())
						scraper.stop();
				}

				System.out.println(silentModeCheckBox);
				System.out.println(destinationTextField.getText());
				System.out.println(onlySaveWhenKeywordCheckBox.isSelected());
				System.out.println(keywordsTextField.getSelectedText());
				scraper = new Scraper(silentModeCheckBox.isSelected(), new File(destinationTextField.getText().replace("\\", "\\\\")), !onlySaveWhenKeywordCheckBox.isSelected(), keywordsTextField.getText().split(" "));
				scraper.start();
				scraper.run();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (scraper != null)
					scraper.stop();
			}
		});
	}
}
