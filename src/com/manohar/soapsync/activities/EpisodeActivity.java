package com.manohar.soapsync.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adapters.EpisodeAdapter;
import com.manohar.soapsync.tasks.DataDownloadTask;

public class EpisodeActivity extends Activity {
	
	private GridView gridView;
	private int selectedShow;
	private int SelectedSeason;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		ActionBar actionBar = Utilities.setCustomActionBar(this, "Episodes");
		setContentView(R.layout.activity_episode);
		selectedShow = Integer.valueOf(getIntent().getExtras().get("SHOW_ID").toString());
		SelectedSeason = Integer.valueOf(getIntent().getExtras().get("SEASON_ID").toString());
		gridView = (GridView)findViewById(R.id.episode_grid);
		final EpisodeAdapter episodeAdapter = new EpisodeAdapter(this, selectedShow, SelectedSeason);
		gridView.setAdapter(episodeAdapter);

		((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP) {
					(new DataDownloadTask(EpisodeActivity.this,episodeAdapter,gridView)).execute();
				}
				return true;
			}
		});
		
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.exit_in, R.anim.exit_out);
		
	}
	
	


}
