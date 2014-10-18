package com.manohar.soapsync.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.activities.HomeActivity;
import com.manohar.soapsync.activities.SplashActivity;
import com.manohar.soapsync.adapters.SeasonsAdapter;
import com.manohar.soapsync.adapters.TVShowListAdapter;
import com.manohar.soapsync.comparators.EpisodeComparator;
import com.manohar.soapsync.comparators.SeasonComparator;
import com.manohar.soapsync.comparators.TVShowComparator;
import com.manohar.soapsync.pojos.Episode;
import com.manohar.soapsync.pojos.Season;
import com.manohar.soapsync.pojos.TVShow;

public class DataDownloadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String result;
	private BaseAdapter adapter;
	private GridView gridView;
	private ActionBar actionBar;
	private static DataDownloadTask dataDownloadTask;
	
	private DataDownloadTask(){
		
	}
	
	public static DataDownloadTask getInstance(Context context,BaseAdapter adapter,GridView gridView,ActionBar actionBar){
		if(dataDownloadTask == null){
			dataDownloadTask = new DataDownloadTask(context, adapter, gridView, actionBar);
		}
		return dataDownloadTask;
	}
	
	
	private DataDownloadTask(Context context,BaseAdapter adapter,GridView gridView,ActionBar actionBar) {
		this.context = context;
		this.adapter = adapter;
		this.gridView = gridView;
		this.actionBar = actionBar;
	}
	
	public static boolean isTaskRunning(){
		if(dataDownloadTask == null)
			return false;
		
		return (AsyncTask.Status.RUNNING == dataDownloadTask.getStatus());
	}

	@Override
	protected void onPreExecute() {
		
		if(actionBar != null ) {
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setText("Refreshing...");
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setEnabled(false);
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setTextColor(Color.parseColor("#CDCDCD"));
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setBackgroundColor(Color.parseColor("#0A82AB"));
		}
		
		if(this.context instanceof SplashActivity) {
		((TextView) ((SplashActivity) this.context)
				.findViewById(R.id.splash_load_status))
				.setText("Going online to find data.");
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		this.result = Utilities
				.getJsonStringFromWebService(Utilities.WEBSERVICE_ENDPOINT);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		if(actionBar != null ) {
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setEnabled(true);
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setTextColor(Color.parseColor("#FFFFFF"));
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setBackgroundColor(Color.parseColor("#0B8CB8"));
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setText("Refresh");
		}
		
		if (this.result == null && this.context instanceof SplashActivity) {
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
						
						Episode episode = new Episode();
						episode.setEpisodeId(Integer.parseInt(episodeKey));
						episode.setPlot(episodeJson.getString("Plot"));
						episode.setEpisodeName(episodeJson.getString("EpisodeName"));
						
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

			if(this.adapter != null ) {
				
				// Needed to refresh the thumbnails on home screen
				if(this.adapter instanceof TVShowListAdapter) {
					this.adapter = new TVShowListAdapter(Utilities.tvShows, this.context);
				}
				
				this.gridView.invalidateViews();
				this.gridView.setAdapter(this.adapter);
			}
			
			if(this.context instanceof SplashActivity) {
				Intent intent = new Intent(((SplashActivity) this.context),
						HomeActivity.class);
				((SplashActivity) this.context).finish();
				((SplashActivity) this.context).startActivity(intent);
			}
			
			Toast.makeText(this.context, "Finished refreshing", Toast.LENGTH_SHORT).show();
			dataDownloadTask = null;

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
