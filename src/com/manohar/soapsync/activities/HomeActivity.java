package com.manohar.soapsync.activities;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adapters.TVShowListAdapter;

public class HomeActivity extends Activity {

	private GridView gridView;
	private BaseAdapter adapter;
	private Thread exitCounterThread;
	private boolean backPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_home);
		
		gridView = (GridView) findViewById(R.id.gridView);

		try {
			this.loadTVShowData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utilities.setActionBarAndListeners(this, adapter, gridView, "Home");
		
	}

	private void loadTVShowData() throws JSONException {
		
		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		this.populateScreen();	
	}

	
	public void populateScreen() throws JSONException {
		adapter = new TVShowListAdapter(Utilities.tvShows, this);
		gridView.setAdapter(adapter);	
	}
	
	@Override
	public void onBackPressed(){
		if(backPressed == true) {
			super.onBackPressed();
			overridePendingTransition(R.anim.quit_in, R.anim.quit_out);
		} else {
		
			Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT).show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						backPressed = true;
						Thread.sleep(1500);
						backPressed = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}
	

}
