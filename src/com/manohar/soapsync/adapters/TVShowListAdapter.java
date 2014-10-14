package com.manohar.soapsync.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.manohar.soapsync.R;
import com.manohar.soapsync.R.drawable;
import com.manohar.soapsync.R.id;
import com.manohar.soapsync.R.layout;
import com.manohar.soapsync.activities.SeasonActivity;
import com.manohar.soapsync.customviews.SquareImageView;
import com.manohar.soapsync.pojos.TVShow;
import com.squareup.picasso.Picasso;

public class TVShowListAdapter extends BaseAdapter {

	private List<TVShow> tvShow = null;
	private Context context = null;
	private static LayoutInflater inflater;

	public TVShowListAdapter(List<TVShow> items, Context context) {
		this.tvShow = items;
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.tvShow.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(final int id, View recycledView, ViewGroup parent) {
		if (recycledView == null) {
			recycledView = inflater.inflate(R.layout.home_show_item_view, null);
		}

		((TextView) recycledView.findViewById(R.id.item_show_name))
				.setText(this.tvShow.get(id).getShowName());
		((TextView) recycledView.findViewById(R.id.item_show_name))
				.setBackgroundColor(this.tvShow.get(id).getShowColor());

		
		Picasso.with(this.context)
				.load(this.tvShow.get(id).getShowThumbNail())
				.placeholder(R.drawable.image_placeholder)
				.into(((SquareImageView) recycledView
						.findViewById(R.id.home_image_item)));

		recycledView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View targetView, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(TVShowListAdapter.this.context,SeasonActivity.class);
					intent.putExtra("SHOW_ID", id);
					TVShowListAdapter.this.context.startActivity(intent);
				}
				return true;
			}
		});

		return recycledView;
	}

}
