package com.manohar.soapsync.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.R.id;
import com.manohar.soapsync.activities.HomeActivity;
import com.manohar.soapsync.activities.SplashActivity;
import com.manohar.soapsync.comparators.EpisodeComparator;
import com.manohar.soapsync.comparators.SeasonComparator;
import com.manohar.soapsync.comparators.TVShowComparator;
import com.manohar.soapsync.pojos.Episode;
import com.manohar.soapsync.pojos.Season;
import com.manohar.soapsync.pojos.TVShow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

public class DataDownloadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String result;

	public DataDownloadTask(Context context) {
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
				
				TVShow tvShow = new TVShow();
				tvShow.setSeasons(new ArrayList<Season>());
				tvShow.setShowId(Integer.parseInt(key));
				tvShow.setShowName(obj.getString("Name"));
				tvShow.setShowThumbNail(obj.getString("Image"));
				tvShow.setSummary(obj.getString("Summary"));
				tvShow.setShowColor(Color.parseColor(obj.getString("Color")));
				
				
				JSONObject seasons = (JSONObject)obj.get("Seasons");
				Iterator<String> seasonIterator = seasons.keys();
				while(seasonIterator.hasNext()){
					String seasonKey = seasonIterator.next();
					
					Season season = new Season();
					season.setEpisodes(new ArrayList<Episode>());
					season.setSeasonId(Integer.parseInt(seasonKey));
					
					JSONObject episodes = ((JSONObject)((JSONObject)seasons.get(seasonKey)).get("Episodes"));
					Iterator<String> episodesIterator = episodes.keys();
					
					while(episodesIterator.hasNext()){
						String episodeKey = episodesIterator.next();
						
						JSONObject episodeJson = (JSONObject)episodes.get(episodeKey);
						String plot = episodeJson.getString("Plot");
						
						Episode episode = new Episode();
						episode.setEpisodeId(Integer.parseInt(episodeKey));
						episode.setPlot(plot);
						
						season.getEpisodes().add(episode);
						
					}
					Collections.sort(season.getEpisodes(),new EpisodeComparator());
					tvShow.getSeasons().add(season);
					
				}
				Collections.sort(tvShow.getSeasons(),new SeasonComparator());
				Utilities.tvShows.add(tvShow);
			}
			Collections.sort(Utilities.tvShows,new TVShowComparator());
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