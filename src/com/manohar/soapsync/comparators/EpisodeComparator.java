package com.manohar.soapsync.comparators;

import java.util.Comparator;

import com.manohar.soapsync.pojos.Episode;

public class EpisodeComparator implements Comparator<Episode> {

	@Override
	public int compare(Episode ep1, Episode ep2) {
		if(ep1.getEpisodeId() >= ep2.getEpisodeId()){
			return 1;
		} else {
			return -1;
		}
	}

}
