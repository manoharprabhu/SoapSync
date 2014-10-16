package com.manohar.soapsync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;

public class EpisodeAdapter extends BaseAdapter {

	private static LayoutInflater layoutInflater;
	private Context context;
	private int selectedShow;
	private int SelectedSeason;

	public EpisodeAdapter(Context context, int selectedShow, int SelectedSeason) {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.selectedShow = selectedShow;
		this.SelectedSeason = SelectedSeason;
	}

	@Override
	public int getCount() {
		return Utilities.tvShows.get(selectedShow).getSeasons()
				.get(SelectedSeason).getEpisodes().size();
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
	public View getView(final int id, View view, ViewGroup parent) {
		// System.out.println(id);
		if (view == null) {
			view = layoutInflater.inflate(R.layout.episode_item, null);
		}
		((TextView) view.findViewById(R.id.episode_description))
				.setText(Utilities.tvShows.get(selectedShow).getSeasons()
						.get(SelectedSeason).getEpisodes().get(id).getPlot());
		((TextView) view.findViewById(R.id.episode_name))
				.setText(Utilities.tvShows.get(selectedShow).getSeasons()
						.get(SelectedSeason).getEpisodes().get(id)
						.getEpisodeName());
		((TextView) view.findViewById(R.id.episode_number)).setText(String
				.valueOf(id + 1));

		if (Utilities.tvShows.get(selectedShow).getSeasons()
				.get(SelectedSeason).getEpisodes().get(id).isPlotVisible()) {
			((TextView) view.findViewById(R.id.episode_description))
					.setVisibility(View.VISIBLE);
			((Button) view.findViewById(R.id.episode_show_plot_button))
					.setVisibility(View.GONE);
		} else {
			((TextView) view.findViewById(R.id.episode_description))
					.setVisibility(View.GONE);
			((Button) view.findViewById(R.id.episode_show_plot_button))
					.setVisibility(View.VISIBLE);
		}

		((CheckBox) view.findViewById(R.id.episode_watched_checkbox))
				.setChecked(Utilities.tvShows.get(selectedShow).getSeasons()
						.get(SelectedSeason).getEpisodes().get(id)
						.isEpisodeWatched());

		final TextView plotView = ((TextView) view
				.findViewById(R.id.episode_description));

		((Button) view.findViewById(R.id.episode_show_plot_button))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						((Button) v).setVisibility(View.GONE);
						((Button) v).setOnClickListener(null);
						plotView.setVisibility(View.VISIBLE);
						Utilities.tvShows.get(selectedShow).getSeasons()
								.get(SelectedSeason).getEpisodes().get(id)
								.setPlotVisible(true);
					}
				});

		((CheckBox) view.findViewById(R.id.episode_watched_checkbox))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Utilities.tvShows.get(selectedShow).getSeasons()
								.get(SelectedSeason).getEpisodes().get(id)
								.setEpisodeWatched(((CheckBox) v).isChecked());
					}
				});

		return view;
	}

}
