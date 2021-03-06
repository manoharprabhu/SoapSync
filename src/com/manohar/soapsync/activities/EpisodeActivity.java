package com.manohar.soapsync.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adapters.EpisodeAdapter;

public class EpisodeActivity extends Activity {
	
	private GridView gridView;
	private int selectedShow;
	private int SelectedSeason;
	private BaseAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_episode);
		selectedShow = Integer.valueOf(getIntent().getExtras().get("SHOW_ID").toString());
		SelectedSeason = Integer.valueOf(getIntent().getExtras().get("SEASON_ID").toString());
		gridView = (GridView)findViewById(R.id.episode_grid);
		adapter = new EpisodeAdapter(this, selectedShow, SelectedSeason);
		Utilities.setActionBarAndListeners(this, adapter, gridView, "Episodes");
		gridView.setAdapter(adapter);

		
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.exit_in, R.anim.exit_out);
		
	}
	
	


}
