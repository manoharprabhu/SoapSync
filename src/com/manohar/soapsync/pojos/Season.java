package com.manohar.soapsync.pojos;

import java.io.Serializable;
import java.util.List;

public class Season implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 354733715071584809L;
	private int seasonId;
	private List<Episode> episodes;
	public int getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	
	@Override
	public String toString(){
		return episodes.toString();
	}
}
