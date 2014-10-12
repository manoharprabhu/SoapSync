package com.manohar.soapsync.activities;

import java.util.List;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.R.anim;
import com.manohar.soapsync.R.layout;
import com.manohar.soapsync.pojos.Season;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class SeasonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_show_detail);
		Utilities.setCustomActionBar(this, "Seasons");
		
		int selectedIndex = Integer.valueOf(getIntent().getExtras().get("SHOW_ID").toString());
		List<Season> selectedSeasons = Utilities.tvShows.get(selectedIndex).getSeasons();
		
		Toast.makeText(this, selectedSeasons.get(0).getEpisodes().get(0).getPlot(), Toast.LENGTH_SHORT).show();
	}


}
