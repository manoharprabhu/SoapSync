package com.manohar.soapsync.pojos;

import java.io.Serializable;

public class Episode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7405713740237427629L;
	
	private int episodeId;
	private String plot;
	private String episodeName;
	private String originalAiredDate;
	private String imdbRating;
	
	
	private boolean isEpisodeWatched;
	
	
	public String getOriginalAiredDate() {
		return originalAiredDate;
	}
	public void setOriginalAiredDate(String originalAiredDate) {
		this.originalAiredDate = originalAiredDate;
	}
	public String getImdbRating() {
		return imdbRating;
	}
	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}
	
	
	public boolean isEpisodeWatched() {
		return isEpisodeWatched;
	}
	public void setEpisodeWatched(boolean isEpisodeWatched) {
		this.isEpisodeWatched = isEpisodeWatched;
	}
	public String getEpisodeName() {
		return episodeName;
	}
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}
	public int getEpisodeId() {
		return episodeId;
	}
	public void setEpisodeId(int episodeId) {
		this.episodeId = episodeId;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	@Override
	public String toString(){
		return this.plot + " ";
	}

}
