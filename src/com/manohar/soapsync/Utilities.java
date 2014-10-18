package com.manohar.soapsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.manohar.soapsync.activities.HomeActivity;
import com.manohar.soapsync.pojos.Episode;
import com.manohar.soapsync.pojos.Season;
import com.manohar.soapsync.pojos.TVShow;
import com.manohar.soapsync.tasks.DataDownloadTask;

public class Utilities {
	public static List<TVShow> tvShows = null;
	
	private static Integer WINDOW_WIDTH = null;
	private static Integer WINDOW_HEIGHT = null;
	private static Random random = new Random(252352342434L);
	public static final String WEBSERVICE_ENDPOINT = "http://manoharprabhu.github.io/SoapSync/showlist.json";
    
	private static void loadScreenDimensions(Context context) {
		WINDOW_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
		WINDOW_HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getScreenWidth(Context context) {
		if (WINDOW_WIDTH == null) {
			loadScreenDimensions(context);
		}
		return WINDOW_WIDTH;
	}

	public static int getScreenHeight(Context context) {
		if (WINDOW_HEIGHT == null) {
			loadScreenDimensions(context);
		}
		return WINDOW_HEIGHT;
	}

	public static int convertDptoPx(int dp, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static Bitmap getBitmapFromBase64(String base64) {
		byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return decodedByte;
	}
	
	@SuppressWarnings("unchecked")
	public static List<TVShow> loadTVShowDataFromDisk(Context context){
		try {
		 FileInputStream inputStream = context.openFileInput("tvshows.srl");
		 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		List<TVShow> tvShows = (List<TVShow>)objectInputStream.readObject();
		 objectInputStream.close();
		 return tvShows;
		} catch(Exception e){
			return null;
		}	
	}
	
	public static void overwriteTVShowDataOnDisk(Context context,List<TVShow> tvShow){
		
		try {
			FileOutputStream fileOutputStream = context.openFileOutput("tvshows.srl", Context.MODE_PRIVATE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(tvShow);
		objectOutputStream.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveTVShowDataToDisk(Context context,List<TVShow> newShowData){
		try {
		
		File file = context.getFileStreamPath("tvshows.srl");
		if(file.exists()) {
		
			List<TVShow> oldShowData = loadTVShowDataFromDisk(context);
			 
			 for(int i=0;i<oldShowData.size(); i++){
				 for(int j=0;j<oldShowData.get(i).getSeasons().size() ; j++ ){
					 for(int k=0;k<oldShowData.get(i).getSeasons().get(j).getEpisodes().size();k++){
						 newShowData.get(i).getSeasons().get(j).getEpisodes().get(k).setEpisodeWatched(
								 oldShowData.get(i).getSeasons().get(j).getEpisodes().get(k).isEpisodeWatched()
						 );
						  
					 }
				 }
			 }
			 
		}

	 	FileOutputStream fileOutputStream = context.openFileOutput("tvshows.srl", Context.MODE_PRIVATE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(newShowData);
		objectOutputStream.close();
		
		} catch(Exception e){
		
		}
	}
	
	public static String getJsonStringFromWebService(String url){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		try {
			HttpResponse response = client.execute(get);
			InputStream stream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			StringBuilder builder = new StringBuilder();
			String line;
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				builder.append(line);
			}

			return builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	}
	
	
	private static ActionBar setCustomActionBar(Context context,String title){
		View view = Utilities.getCustomActionBarView(context,title);
		ActionBar actionBar = ((Activity)context).getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(view);
		return actionBar;
	}
	
	private static View getCustomActionBarView(Context context,String title){
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.custom_action_bar, null);
		((TextView)view.findViewById(R.id.custom_actionbar_title)).setText(title);
		
		if(DataDownloadTask.isTaskRunning()){
			((Button)view.findViewById(R.id.custom_actionbar_refresh_button)).setText("Refreshing...");
		} else {
			((Button)view.findViewById(R.id.custom_actionbar_refresh_button)).setText("Refresh");
		}
		
		return view;
	}
	
	public static void setActionBarAndListeners(final Context context,final BaseAdapter adapter,final GridView gridView, final String title){
		final ActionBar actionBar = setCustomActionBar(context, title);
		if(!(context instanceof HomeActivity)){
			((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setVisibility(View.GONE);
		} else {
		((Button)actionBar.getCustomView().findViewById(R.id.custom_actionbar_refresh_button)).setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_UP) {
						if(!DataDownloadTask.isTaskRunning()) {
							(DataDownloadTask.getInstance(context, adapter, gridView, actionBar)).execute();
						}
					}
					return true;
				}
			});
		}
	}

}
