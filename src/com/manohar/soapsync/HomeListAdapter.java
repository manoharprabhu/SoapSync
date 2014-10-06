package com.manohar.soapsync;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeListAdapter extends BaseAdapter {

	private List<TVShow> tvShow = null;
	private Context context = null;
	private static LayoutInflater inflater;

	public HomeListAdapter(List<TVShow> items, Context context) {
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
	public View getView(int id, View recycledView, ViewGroup arg2) {
		if (recycledView == null) {
			System.out.println("ALLOCATED SPACE FOR THE FUCKING VIEW");
			recycledView = inflater.inflate(R.layout.home_show_item_view, null);
		}

		((TextView) recycledView.findViewById(R.id.item_show_name))
				.setText(this.tvShow.get(id).getShowName());
		((TextView) recycledView.findViewById(R.id.item_show_name))
		.setBackgroundColor(Utilities.pickColorAtIndex(id));

		((TextView) recycledView.findViewById(R.id.home_text_summary))
				.setText(this.tvShow.get(id).getSummary());
		((TextView) recycledView.findViewById(R.id.home_text_summary))
			.setTextColor(Utilities.pickColorAtIndex(id));
		((ImageView) recycledView.findViewById(R.id.home_image_item))
				.setImageBitmap(Utilities.getBitmapFromBase64(this.tvShow.get(
						id).getShowThumbNail()));

		return recycledView;
	}

}
