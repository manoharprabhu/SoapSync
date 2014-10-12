package com.manohar.soapsync.comparators;

import java.util.Comparator;

import com.manohar.soapsync.pojos.Season;

public class SeasonComparator implements Comparator<Season>{

	@Override
	public int compare(Season s1, Season s2) {
	if(s1.getSeasonId() >= s2.getSeasonId()){
		return 1;
	} else {
		return -1;
	}
	
	}

}
