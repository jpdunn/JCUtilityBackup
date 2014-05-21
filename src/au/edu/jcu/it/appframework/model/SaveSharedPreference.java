package au.edu.jcu.it.appframework.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

	static final String PREF_USER_NAME = "username";
	static final String PREF_PASSWORD = "password";
	static final String PREF_USER_TYPE = "userType";

	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public static void setUserName(Context ctx, String userName) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_NAME, userName);
		editor.commit();
	}

	public static String getUserName(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
	}
	
	public static void setPassword(Context ctx, String password) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_PASSWORD, password);
		editor.commit();
	}
	
	public static void setUserType(Context ctx, String userType) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_TYPE, userType);
		editor.commit();
	}
	
	public static String getUserType(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_TYPE, "");
	}

	public static String getPassword(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
	}
	
	
	public static void logout(Context ctx){		
		Editor editor = getSharedPreferences(ctx).edit();
		editor.clear();
		editor.commit();
	}
}
