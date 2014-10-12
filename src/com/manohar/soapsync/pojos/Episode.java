package com.manohar.soapsync.pojos;

import java.io.Serializable;

public class Episode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7405713740237427629L;
	private int episodeId;
	private String plot;
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
