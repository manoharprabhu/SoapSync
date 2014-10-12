package com.manohar.soapsync.pojos;

import java.io.Serializable;
import java.util.List;

import android.graphics.Color;

public class TVShow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8976075719328518858L;
	private String showName;
	private String showThumbNail;
	private int showId;
	private String summary;
	private int showColor;
	

	public int getShowColor() {
		return showColor;
	}

	public void setShowColor(int showColor) {
		this.showColor = showColor;
	}

	private List<Season> seasons;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getShowThumbNail() {
		return showThumbNail;
	}

	public void setShowThumbNail(String showThumbNail) {
		this.showThumbNail = showThumbNail;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	@Override
	public String toString() {
		return this.showId + " " + this.showName + " " + this.seasons.toString();

	}

}
