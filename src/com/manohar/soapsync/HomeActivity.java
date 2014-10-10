package com.manohar.soapsync;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends Activity {

	private GridView gridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Utilities.setCustomActionBar(this, "Home");
		
		gridView = (GridView) findViewById(R.id.gridView);

		try {
			this.loadTVShowData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadTVShowData() throws JSONException {
		
		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		this.populateScreen();
		
		
	}

	
	public void populateScreen() throws JSONException {
		gridView.setAdapter(new HomeListAdapter(Utilities.tvShows, this));
	}

}
