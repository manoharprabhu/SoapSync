package com.manohar.soapsync.activities;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.GridView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adapters.TVShowListAdapter;

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
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.quit_in, R.anim.quit_out);
		
	}
	

}
