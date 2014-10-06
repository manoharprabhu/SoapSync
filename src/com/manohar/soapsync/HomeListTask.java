package com.manohar.soapsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class HomeListTask extends AsyncTask<Void, Void, Void> {

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
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(
				"http://manoharprabhu.github.io/SoapSync/showlist.json");

		try {
			HttpResponse response = client.execute(get);
			InputStream stream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			StringBuilder builder = new StringBuilder();
			String line;
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				builder.append(line);
			}

			this.result = builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			Utilities.saveTVShowData(this.context, ((HomeActivity) this.context).getTvShows());
			((HomeActivity) this.context).populateScreen();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
