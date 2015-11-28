/**
 * aTunes 1.6.0
 * Copyright (C) 2006-2007 Alex Aranda (fleax) alex.aranda@gmail.com
 *
 * http://www.atunes.org
 * http://sourceforge.net/projects/atunes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package net.sourceforge.atunes.gui.views.panels;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.sourceforge.atunes.gui.FontSingleton;
import net.sourceforge.atunes.kernel.controllers.fileProperties.FilePropertiesController;
import net.sourceforge.atunes.kernel.modules.repository.audio.AudioFile;
import net.sourceforge.atunes.kernel.modules.repository.tags.tag.ID3v2Tag;
import net.sourceforge.atunes.kernel.modules.repository.tags.tag.NonMp3Tag;
import net.sourceforge.atunes.utils.StringUtils;
import net.sourceforge.atunes.utils.TimeUtils;
import net.sourceforge.atunes.utils.language.LanguageTool;


/**
 * @author fleax
 *
 */
public class FilePropertiesPanel extends JPanel {

	private static final long serialVersionUID = 6097305595858691246L;

	private JPanel songPanel;
	private JLabel pictureLabel;
	private JLabel fileNameLabel;
	private JLabel songLabel;
	private JLabel trackLabel;
	private JLabel yearLabel;
	private JLabel genreLabel;
	private JLabel bitrateLabel;
	private JLabel frequencyLabel;
		
	public FilePropertiesPanel() {
		super(new BorderLayout());
		addContent();
	}
	
	private void addContent() {
		getSongPanel().setVisible(false);
		getPictureLabel().setVisible(false);
		add(getSongPanel(), BorderLayout.CENTER);
	}
	
	public JLabel getPictureLabel() {
		return pictureLabel;
	}

	public JLabel getFileNameLabel() {
		return fileNameLabel;
	}

	public JLabel getGenreLabel() {
		return genreLabel;
	}

	public JLabel getSongLabel() {
		return songLabel;
	}

	public JLabel getTrackLabel() {
		return trackLabel;
	}

	public JLabel getYearLabel() {
		return yearLabel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600,300);
		frame.add(new FilePropertiesPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}


	public JLabel getBitrateLabel() {
		return bitrateLabel;
	}

	public JLabel getFrequencyLabel() {
		return frequencyLabel;
	}
	
	public JPanel getSongPanel() {
		if (songPanel == null) {
			songPanel = new JPanel(new GridBagLayout());
			pictureLabel = new JLabel();
			pictureLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0; c.gridy = 0; c.gridheight = 7; c.insets = new Insets(5,10,5,5);
			songPanel.add(pictureLabel, c);
			c.gridx = 1; c.gridy = 0; c.gridwidth = 3; c.gridheight = 1; c.weightx = 1; c.weighty = 1; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,10,0,10);
			songLabel = new JLabel();
			songLabel.setFont(FontSingleton.GENERAL_FONT);
			songLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			songPanel.add(songLabel, c);
			c.gridy = 1; c.insets = new Insets(0,10,0,10);
			fileNameLabel = new JLabel();
			fileNameLabel.setFont(FontSingleton.GENERAL_FONT);
			songPanel.add(fileNameLabel, c);
			c.gridy = 2; c.gridwidth = 1; c.insets = new Insets(0,10,0,5);
			trackLabel = new JLabel();
			trackLabel.setFont(FontSingleton.GENERAL_FONT);
			songPanel.add(trackLabel, c);
			c.gridy = 3;
			yearLabel = new JLabel();
			yearLabel.setFont(FontSingleton.GENERAL_FONT);
			songPanel.add(yearLabel, c);
			c.gridy = 4; c.insets = new Insets(0,10,5,10);
			genreLabel = new JLabel();
			genreLabel.setFont(FontSingleton.GENERAL_FONT);
			songPanel.add(genreLabel, c);
			
			c.gridx = 2; c.gridy = 2; c.insets = new Insets(0,5,0,5);
			bitrateLabel = new JLabel();
			bitrateLabel.setFont(FontSingleton.GENERAL_FONT);
			songPanel.add(bitrateLabel, c);
			frequencyLabel = new JLabel();
			frequencyLabel.setFont(FontSingleton.GENERAL_FONT);
			c.gridy = 3;
			songPanel.add(frequencyLabel, c);
		}
		return songPanel;
	}

	public void fillSongProperties(AudioFile currentFile, FilePropertiesController filePropertiesController) {
		if (currentFile != null && currentFile.getTag() != null) {
			long size = currentFile.length();
			getFileNameLabel().setText("<html><b>" + LanguageTool.getString("FILE") + ":</b>    "
					+ currentFile.getName() + " (" + StringUtils.fromByteToMegaOrGiga(size) + ")</html>");
			getSongLabel().setText("<html><b>" + LanguageTool.getString("SONG") + ":</b>    "
					+ currentFile.getTitleOrFileName() + " - " + currentFile.getArtist() + " - "
					+ currentFile.getAlbum() + " (" + TimeUtils.seconds2String(currentFile.getDuration()) + ")</html>");
			if (currentFile.getTag() instanceof ID3v2Tag) {
				if (((ID3v2Tag) currentFile.getTag()).getTrackNumber() > 0)
					getTrackLabel().setText("<html><b>" + LanguageTool.getString("TRACK") + ":</b>    "
							+ ((ID3v2Tag) currentFile.getTag()).getTrackNumber());
				else
					getTrackLabel().setText("<html><b>" + LanguageTool.getString("TRACK") + ':');
			} else
				getTrackLabel().setText("<html><b>" + LanguageTool.getString("TRACK") + ":</b>    "
						+ ((NonMp3Tag) currentFile.getTag()).getTrackNumber());
			if (currentFile.getTag().getYear() >= 0)
				getYearLabel().setText(
						"<html><b>" + LanguageTool.getString("YEAR") + ":</b>    " + currentFile.getTag().getYear());
			else
				getYearLabel().setText("<html><b>" + LanguageTool.getString("YEAR") + ':');
			getGenreLabel().setText(
					"<html><b>" + LanguageTool.getString("GENRE") + ":</b>    " + currentFile.getTag().getGenre());
			filePropertiesController.refreshFavoriteIcons();
		} else {
			getFileNameLabel().setText("<html><b>" + LanguageTool.getString("FILE") + ":</b>    ");
			getSongLabel().setText("<html><b>" + LanguageTool.getString("SONG") + ":</b>    ");
			getTrackLabel().setText("<html><b>" + LanguageTool.getString("TRACK") + ":</b>    ");
			getYearLabel().setText("<html><b>" + LanguageTool.getString("YEAR") + ":</b>    ");
			getGenreLabel().setText("<html><b>" + LanguageTool.getString("GENRE") + ":</b>    ");
		}
	}
	
}
