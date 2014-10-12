package com.manohar.soapsync.comparators;

import java.util.Comparator;

import com.manohar.soapsync.pojos.TVShow;

public class TVShowComparator implements Comparator<TVShow>{

	@Override
	public int compare(TVShow tv1, TVShow tv2) {
		if(tv1.getShowId() >= tv2.getShowId()){
			return 1;
		} else {
			return -1;
		}
	}

}
