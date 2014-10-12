package com.manohar.soapsync.activities;

import org.json.JSONException;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.R.anim;
import com.manohar.soapsync.R.id;
import com.manohar.soapsync.R.layout;
import com.manohar.soapsync.adaoters.TVShowListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends Activity {

	private GridView gridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
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
		gridView.setAdapter(new TVShowListAdapter(Utilities.tvShows, this));
	}

}
