package com.manohar.soapsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
		tvShows = Utilities.loadTVShowDataFromDisk(this);

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

 class HomeListTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private ProgressDialog progressDialog;
	private String result;
	

	public HomeListTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(this.context);
		progressDialog.setIndeterminate(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle("Getting your favorite TV shows");
		progressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		this.result = Utilities.getJsonStringFromWebService(Utilities.WEBSERVICE_ENDPOINT);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		if(this.result == null){
			progressDialog.setTitle("Couldn't load the TV show data. Network problem");
			progressDialog.setCancelable(true);
			return;
		}
		progressDialog.dismiss();
		try {
			((HomeActivity) this.context).setTvShows(new ArrayList<TVShow>());
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
				((HomeActivity) this.context).getTvShows().add(item);
			}
			Utilities.saveTVShowDataToDisk(this.context, ((HomeActivity) this.context).getTvShows());
			((HomeActivity) this.context).populateScreen();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}