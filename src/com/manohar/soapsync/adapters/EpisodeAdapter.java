package com.manohar.soapsync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;

public class EpisodeAdapter extends BaseAdapter{
	
	private static LayoutInflater layoutInflater;
	private Context context;
	private int selectedShow;
	private int SelectedSeason;
	
	
	public EpisodeAdapter(Context context,int selectedShow,int SelectedSeason){
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.selectedShow = selectedShow;
		this.SelectedSeason = SelectedSeason;
	}

	@Override
	public int getCount() {
		return Utilities.tvShows.get(selectedShow).getSeasons().get(SelectedSeason).getEpisodes().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int id, View view, ViewGroup parent) {
		if(view == null){
			view = layoutInflater.inflate(R.layout.episode_item, null);
		}
		
		((TextView)view.findViewById(R.id.episode_description)).setText(Utilities.tvShows.get(selectedShow).getSeasons().get(SelectedSeason).getEpisodes().get(id).getPlot());
		return view;
	}

}
