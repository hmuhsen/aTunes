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

package net.sourceforge.atunes.kernel.controllers.fileProperties;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;

import net.sourceforge.atunes.Constants;
import net.sourceforge.atunes.gui.images.ImageLoader;
import net.sourceforge.atunes.gui.views.panels.FilePropertiesPanel;
import net.sourceforge.atunes.kernel.HandlerProxy;
import net.sourceforge.atunes.kernel.controllers.model.PanelController;
import net.sourceforge.atunes.kernel.modules.repository.audio.AudioFile;
import net.sourceforge.atunes.kernel.modules.repository.tags.tag.ID3v2Tag;
import net.sourceforge.atunes.kernel.modules.repository.tags.tag.NonMp3Tag;
import net.sourceforge.atunes.kernel.utils.AudioFilePictureUtils;
import net.sourceforge.atunes.utils.StringUtils;
import net.sourceforge.atunes.utils.TimeUtils;
import net.sourceforge.atunes.utils.language.LanguageTool;


/**
 * @author fleax
 *
 */
public class FilePropertiesController extends PanelController {

	AudioFile currentFile;
	
	FilePropertiesPanel panelControlled;
		
	public FilePropertiesController(FilePropertiesPanel panelControlled) {
		super(panelControlled);
		this.panelControlled = panelControlled;
		addBindings();
		addStateBindings();
	}
	
	protected void addBindings() {
		panelControlled.getPictureLabel().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				HandlerProxy.getVisualHandler().showImageDialog(currentFile);
			}
		});
	}
	
	protected void addStateBindings() {
	}
	
	protected void notifyReload() {}
	
	public void clear() {
		currentFile = null;
		panelControlled.getPictureLabel().setIcon(null);
		panelControlled.getPictureLabel().setVisible(false);
		panelControlled.getSongPanel().setVisible(false);
	}
	
	public void updateValues(AudioFile file) {
		currentFile = file;
		Thread updaterThread = new Thread() {
			public void run() {
				// Song properties
				panelControlled.fillSongProperties(currentFile, this);
				// File Properties
				fillFileProperties();
				// Picture
				fillPicture();
				panelControlled.getSongPanel().setVisible(true);
			}
		};
		updaterThread.start();
	}
	
	public void refreshFavoriteIcons() {
		if (currentFile == null)
			return;
		boolean favorite = HandlerProxy.getRepositoryHandler().getFavoriteSongsInfo().containsValue(currentFile) ||
						   HandlerProxy.getRepositoryHandler().getFavoriteArtistsInfo().containsKey(currentFile.getArtist()) ||
						   HandlerProxy.getRepositoryHandler().getFavoriteAlbumsInfo().containsKey(currentFile.getAlbum());
		
		panelControlled.getSongLabel().setIcon(favorite ? ImageLoader.FAVORITE : null);
	}
	
	public void refreshPicture() {
		fillPicture();
	}
	
	void fillPicture() {
		ImageIcon picture = AudioFilePictureUtils.getInsidePicture(currentFile, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
		if (picture == null)
			picture = AudioFilePictureUtils.getExternalPicture(currentFile, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
		
		if (picture != null) {
			panelControlled.getPictureLabel().setPreferredSize(new Dimension(picture.getIconWidth(), picture.getIconHeight()));
			panelControlled.getPictureLabel().setIcon(picture);
			panelControlled.getPictureLabel().setVisible(true);
		}
		else {
			panelControlled.getPictureLabel().setIcon(null);
			panelControlled.getPictureLabel().setVisible(false);
		}
	}
	
	void fillFileProperties() {
		panelControlled.getBitrateLabel().setText("<html><b>" + LanguageTool.getString("BITRATE") + ":</b>    " + currentFile.getBitrate() + " Kbps");
		panelControlled.getFrequencyLabel().setText("<html><b>" + LanguageTool.getString("FREQUENCY") + ":</b>    " + currentFile.getFrequency() + " Hz");
	}		
}
