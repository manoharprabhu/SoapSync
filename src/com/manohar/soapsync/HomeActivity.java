package com.manohar.soapsync;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends Activity {

	private GridView gridView;
	private static List<TVShow> tvShows = null;

	public List<TVShow> getTvShows() {
		return tvShows;
	}

	public void setTvShows(List<TVShow> homeShowItems) {
		tvShows = homeShowItems;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gridView = (GridView) findViewById(R.id.gridView);

		this.loadTVShowData();
	}

	private void loadTVShowData() {
		/*
		 * Load the serialized tv shows from disk, if exists
		 */
		tvShows = Utilities.loadTVShowData(this);

		try {
			if (tvShows != null) {
				this.populateScreen();
			} else {
				(new HomeListTask(this)).execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * This is the callback method that will be executed after loading the tv
	 * shows data from HomeListTask
	 */
	public void populateScreen() throws JSONException {
		gridView.setAdapter(new HomeListAdapter(HomeActivity.tvShows, this));
	}

}
