package net.sourceforge.atunes.model.player;

import java.util.Comparator;

import net.sourceforge.atunes.kernel.modules.repository.audio.AudioFile;


public class PlayListDurationComparator implements Comparator {

	public static PlayListDurationComparator comparator = new PlayListDurationComparator();
	
	public int compare(Object o1, Object o2) {
		// returns 0 or 1
		AudioFile f1 = (AudioFile) o1;
		AudioFile f2 = (AudioFile) o2;
		Long d1 = f1.getDuration();
		Long d2 = f2.getDuration();
		if (d1.equals(d2)) {
			if (f1.getGenre().equals(f2.getGenre())) {
				if (f1.getArtist().equals(f2.getArtist())) {
					if (f1.getAlbum().equals(f2.getAlbum()))
						return f1.getTrackNumber().compareTo(f2.getTrackNumber());
					return f1.getAlbum().compareTo(f2.getAlbum());
				}
				return f1.getArtist().compareTo(f2.getArtist());
			}
			return f1.getGenre().compareTo(f2.getGenre());
		}
		return d1.compareTo(d2);
	}

}