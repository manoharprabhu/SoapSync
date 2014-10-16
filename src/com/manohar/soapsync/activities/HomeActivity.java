package com.manohar.soapsync.activities;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adapters.TVShowListAdapter;
import com.manohar.soapsync.tasks.DataDownloadTask;

public class HomeActivity extends Activity {

	private GridView gridView;
	private ActionBar actionBar;
	private TVShowListAdapter tvShowListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_home);
		actionBar = Utilities.setCustomActionBar(this, "Home");
		
		gridView = (GridView) findViewById(R.id.gridView);

		try {
			this.loadTVShowData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP) {
					(new DataDownloadTask(HomeActivity.this,tvShowListAdapter,gridView)).execute();
				}
				return true;
			}
		});
		
	}

	private void loadTVShowData() throws JSONException {
		
		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		this.populateScreen();
		
		
	}

	
	public void populateScreen() throws JSONException {
		tvShowListAdapter = new TVShowListAdapter(Utilities.tvShows, this);
		gridView.setAdapter(tvShowListAdapter);	
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.quit_in, R.anim.quit_out);
		
	}
	

}
