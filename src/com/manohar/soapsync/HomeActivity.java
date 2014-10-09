package com.manohar.soapsync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends Activity {

	private GridView gridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gridView = (GridView) findViewById(R.id.gridView);

		try {
			this.loadTVShowData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadTVShowData() throws JSONException {
		/*
		 * Load the serialized tv shows from disk, if exists
		 */
		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		this.populateScreen();
		
		
	}

	/*
	 * This is the callback method that will be executed after loading the tv
	 * shows data from HomeListTask
	 */
	public void populateScreen() throws JSONException {
		gridView.setAdapter(new HomeListAdapter(Utilities.tvShows, this));
	}

}
