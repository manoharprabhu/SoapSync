package com.manohar.soapsync.adapters;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.activities.EpisodeActivity;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SeasonsAdapter extends BaseAdapter {

	private Context context;
	private int showIndex;
	private static LayoutInflater inflater;
	
	public SeasonsAdapter(Context context,int showIndex){
		this.context = context;
		this.showIndex = showIndex;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return Utilities.tvShows.get(showIndex).getSeasons().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return showIndex;
	}

	@Override
	public View getView(final int id, View recycledView, ViewGroup parent) {
		if (recycledView == null) {
			recycledView = inflater.inflate(R.layout.season_item, null);
		}
		
		((TextView)recycledView.findViewById(R.id.season_show_name)).setText("Season " + (id+1));
		((TextView)recycledView.findViewById(R.id.season_show_name)).setBackgroundColor(Utilities.tvShows.get(this.showIndex).getShowColor());
		
		Picasso.with(this.context).load(Utilities.tvShows.get(this.showIndex).getShowThumbNail())
		.placeholder(R.drawable.image_placeholder)
		.into(((ImageView)recycledView.findViewById(R.id.season_image)));
		
		recycledView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					Intent intent = new Intent(SeasonsAdapter.this.context,EpisodeActivity.class);
					intent.putExtra("SHOW_ID", showIndex);
					intent.putExtra("SEASON_ID", id);
					SeasonsAdapter.this.context.startActivity(intent);
				}
				return true;
			}
		});
		
		return recycledView;
	}

}
