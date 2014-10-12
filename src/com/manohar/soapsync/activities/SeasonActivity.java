package com.manohar.soapsync.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.adaoters.SeasonsAdapter;
import com.squareup.picasso.Picasso;

public class SeasonActivity extends Activity {

	private GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_show_detail);
		Utilities.setCustomActionBar(this, "Seasons");
		
		int selectedIndex = Integer.valueOf(getIntent().getExtras().get("SHOW_ID").toString());
		
		gridView = (GridView)findViewById(R.id.season_gridview);
		
		gridView.setAdapter(new SeasonsAdapter(this, selectedIndex));
		
		Picasso.with(this).load(Utilities.tvShows.get(selectedIndex).getShowThumbNail()).into((ImageView)findViewById(R.id.season_background_image));
		
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.exit_in, R.anim.exit_out);
		
	}


}
