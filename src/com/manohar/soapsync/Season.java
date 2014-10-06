package com.manohar.soapsync;

import java.io.Serializable;
import java.util.List;

public class Season implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 354733715071584809L;
	private int seasonId;
	private List<Episode> episodes;
}
