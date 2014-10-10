package com.manohar.soapsync;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		((TextView) findViewById(R.id.splash_main_name)).setTextSize(Utilities
				.getScreenWidth(this) / 12);

		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		if (Utilities.tvShows == null) {
			(new DataLoadTask(this)).execute();
		} else {
			((TextView) findViewById(R.id.splash_load_status))
					.setText("Loading data from your phone.");

			Thread transitionThread = new Thread() {
				public void run() {
					try {
						sleep(1500);
						Intent intent = new Intent(getBaseContext(),
								HomeActivity.class);
						SplashActivity.this.finish();
						startActivity(intent);
					} catch (InterruptedException e) {

					}

				}
			};
			transitionThread.start();
		}

	}

}

class DataLoadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String result;

	public DataLoadTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		((TextView) ((SplashActivity) this.context)
				.findViewById(R.id.splash_load_status))
				.setText("Going online to find data.");
	}

	@Override
	protected Void doInBackground(Void... params) {
		this.result = Utilities
				.getJsonStringFromWebService(Utilities.WEBSERVICE_ENDPOINT);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		if (this.result == null) {
			((TextView) ((SplashActivity) this.context)
					.findViewById(R.id.splash_load_status))
					.setText("Problem with network.Couldn't load data.");
			(((SplashActivity) this.context)
					.findViewById(R.id.splash_load_progress))
					.setVisibility(View.INVISIBLE);
			return;
		}
		try {
			Utilities.tvShows = new ArrayList<TVShow>();
			JSONObject jsonObject = new JSONObject(result);
			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				String key = it.next();
				JSONObject obj = (JSONObject) jsonObject.get(key);
				TVShow item = new TVShow();
				item.setShowId(Integer.parseInt(key));
				item.setShowName(obj.getString("Name"));
				item.setShowThumbNail(obj.getString("Image"));
				item.setSummary(obj.getString("Summary"));
				Utilities.tvShows.add(item);
			}
			Utilities.saveTVShowDataToDisk(this.context, Utilities.tvShows);

			Intent intent = new Intent(((SplashActivity) this.context),
					HomeActivity.class);
			((SplashActivity) this.context).finish();
			((SplashActivity) this.context).startActivity(intent);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
