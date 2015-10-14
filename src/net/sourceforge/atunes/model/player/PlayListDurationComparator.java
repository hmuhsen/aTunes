package net.sourceforge.atunes.model.player;

import java.util.Comparator;

import net.sourceforge.atunes.kernel.modules.repository.audio.AudioFile;


public class PlayListDurationComparator implements Comparator {

	public static PlayListDurationComparator comparator = new PlayListDurationComparator();
	
	public int compare(Object o1, Object o2) {
		
		AudioFile f1 = (AudioFile) o1;
		Long d1 = f1.getDuration();
		AudioFile f2 = (AudioFile) o2;
		Long d2 = f2.getDuration();
		// returns 0 or 1
		return d1.compareTo(d2);
	}

}