package com.manohar.soapsync;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utilities {
	private static Integer WINDOW_WIDTH = null;
	private static Integer WINDOW_HEIGHT = null;

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
	public static List<TVShow> loadTVShowData(Context context){
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
	
	public static void saveTVShowData(Context context,List<TVShow> tvShows){
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

}
