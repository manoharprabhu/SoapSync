package com.manohar.soapsync;

import java.io.BufferedReader;
import java.io.FileInputStream;
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;

public class Utilities {
	public static List<TVShow> tvShows = null;
	private static Integer WINDOW_WIDTH = null;
	private static Integer WINDOW_HEIGHT = null;
	private static Random random = new Random(252352342434L);
	public static final String WEBSERVICE_ENDPOINT = "http://manoharprabhu.github.io/SoapSync/showlist.json";
    private static int[] colors = new int[]{Color.argb(180,59, 67, 77),Color.argb(180,242, 10, 37),Color.argb(180,237, 194, 0),Color.argb(180,10, 112, 10),Color.argb(180,38, 0, 252)};
	
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
		 System.out.println("Loaded the shit fron filesystem");
		 return tvShows;
		} catch(Exception e){
			System.out.println("Couldn't load shit");
			return null;
		}	
	}
	
	public static void saveTVShowDataToDisk(Context context,List<TVShow> tvShows){
		try {
		FileOutputStream fileOutputStream = context.openFileOutput("tvshows.srl", Context.MODE_PRIVATE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(tvShows);
		objectOutputStream.close();
		System.out.println("Wrote the shit to filesystem");
		} catch(Exception e){
			System.out.println("Couldnt save shit to disk");
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
	
	public static int pickRandomColor(){
		return colors[random.nextInt(colors.length)];
	}
	
	public static int pickColorAtIndex(int i){
		return colors[i%colors.length];
	}

}
