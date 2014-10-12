package com.manohar.soapsync.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.manohar.soapsync.R;
import com.manohar.soapsync.Utilities;
import com.manohar.soapsync.R.anim;
import com.manohar.soapsync.R.id;
import com.manohar.soapsync.R.layout;
import com.manohar.soapsync.tasks.DataDownloadTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.enter_in, R.anim.enter_out);
		setContentView(R.layout.activity_splash);
		((TextView) findViewById(R.id.splash_main_name)).setTextSize(Utilities
				.getScreenWidth(this) / 12);

		Utilities.tvShows = Utilities.loadTVShowDataFromDisk(this);
		if (Utilities.tvShows == null) {
			(new DataDownloadTask(this)).execute();
		} else {
			((TextView) findViewById(R.id.splash_load_status))
					.setText("Loading data from your phone.");

			Thread transitionThread = new Thread() {
				public void run() {
					try {
						sleep(1500);
						Intent intent = new Intent(getBaseContext(),
								HomeActivity.class);
						SplashActivity.this.finish();
						startActivity(intent);
					} catch (InterruptedException e) {

					}

				}
			};
			transitionThread.start();
		}

	}

}

