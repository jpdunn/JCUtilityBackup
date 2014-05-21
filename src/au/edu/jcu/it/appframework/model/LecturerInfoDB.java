package au.edu.jcu.it.appframework.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LecturerInfoDB {

	private static final String DATABASE_NAME = "LecturerInfoDB";
	
	private static final String DATABASE_TABLE_ROSTER = "Roster";
	public static final String KEY_SUBJECT_CODE = "Subject_Code";
	public static final String KEY_START_TIME = "Start_Time";
	public static final String KEY_END_TIME = "End_Time";
	public static final String KEY_DAY = "Day";
	public static final String KEY_CATEGORY = "Category";
	public static final String KEY_LECTURE_ROOM = "Lecture_Room";
	public static final String KEY_RAW_START = "Raw_Start";
	public static final String KEY_RAW_END = "Raw_End";
	
	private static final String DATABASE_TABLE_USERS = "Users_Logged_In";
	public static final String KEY_EMAIL = "Email";
	public static final String KEY_PASSWORD = "Password";

	private static final int DATABASE_VERSION = 15;
	private DbHelper lecturerHelper;
	private final Context lecturerContext;
	private SQLiteDatabase lecturerDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTables(db);
		}

		public void createTables(SQLiteDatabase db) {
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_ROSTER + " ("
					+ KEY_SUBJECT_CODE + " TEXT, "
					+ KEY_START_TIME + " TEXT, " + KEY_END_TIME + " TEXT, "
					+ KEY_DAY + " TEXT, " + KEY_LECTURE_ROOM + " TEXT, " 
					+ KEY_CATEGORY + " TEXT, "
					+ KEY_RAW_START + " TEXT, " + KEY_RAW_END + " TEXT);");
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_USERS + " ("
					+ KEY_EMAIL + " TEXT UNIQUE, " + KEY_PASSWORD + " TEXT UNIQUE);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			dropTables(db);
		}

		public void dropTables(SQLiteDatabase db) {

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ROSTER);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USERS);
			onCreate(db);
		}
	}

	public LecturerInfoDB(Context c) {
		lecturerContext = c;
	}
	
	public LecturerInfoDB open() throws SQLException {

		lecturerHelper = new DbHelper(lecturerContext);
		lecturerDatabase = lecturerHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		lecturerHelper.close();
	}
	
	
	public long createEntryUser(String email, String password){
		
		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_PASSWORD, password);
		return lecturerDatabase.insert(DATABASE_TABLE_USERS, null, values);
	}
	
	public long createEntryRoster(String rosterIDString,
			String startTimeString, String rosterEndTimeString,
			String rosterDayString, String rosterCategoryString,
			String rosterRoomString, String rawStartString,
			String rawEndString) {

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CODE, rosterIDString);
		values.put(KEY_START_TIME, startTimeString);
		values.put(KEY_END_TIME, rosterEndTimeString);
		values.put(KEY_DAY, rosterDayString);
		values.put(KEY_CATEGORY, rosterCategoryString);
		values.put(KEY_LECTURE_ROOM, rosterRoomString);
		values.put(KEY_RAW_START, rawStartString);
		values.put(KEY_RAW_END, rawEndString);
		return lecturerDatabase.insert(DATABASE_TABLE_ROSTER, null, values);
	}
	

	@SuppressWarnings("rawtypes")
	public ArrayList<Map> getRoster(int dayValue) {

		String query = "SELECT * FROM Roster "
		+ "WHERE Day = " + dayValue;

		Cursor c = lecturerDatabase.rawQuery(query, null);
		ArrayList<Map> allClasses = new ArrayList<Map>();
		int rosterCode = c.getColumnIndex(KEY_SUBJECT_CODE);
		int startTime = c.getColumnIndex(KEY_START_TIME);
		int endTime = c.getColumnIndex(KEY_END_TIME);
		int day = c.getColumnIndex(KEY_DAY);
		int lectureRoom = c.getColumnIndex(KEY_LECTURE_ROOM);
		int category = c.getColumnIndex(KEY_CATEGORY);
		int rawStart = c.getColumnIndex(KEY_RAW_START);
		int rawEnd = c.getColumnIndex(KEY_RAW_END);


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Map<String, String> eachResult = new HashMap<String, String>();

			eachResult.put("Subject_Code", c.getString(rosterCode));
			eachResult.put("Start_Time", c.getString(startTime));
			eachResult.put("End_Time", c.getString(endTime));
			eachResult.put("Day", c.getString(day));
			eachResult.put("Raw_Start", c.getString(rawStart));
			eachResult.put("Raw_End", c.getString(rawEnd));
			eachResult.put("Lecture_Room", c.getString(lectureRoom));
			eachResult.put("Category", c.getString(category));
			allClasses.add(eachResult);
			System.out.println(eachResult.toString());
		}
		return allClasses;
	}
	
	public boolean checkIfNotPopulated() {

		Cursor c = lecturerDatabase.rawQuery("SELECT * FROM "
				+ DATABASE_TABLE_ROSTER, null);

		int query = c.getCount();
		if (query == 0) {
			return true;

		}
		return false;

	}
	
	public boolean checkIfEmailExists(String email) {

		Cursor c = lecturerDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE_USERS
				+ " WHERE Email='" + email + "'", null);

		int query = c.getCount();	
		if (query == 0) {
			return true;

		}
		return false;

	}
	
	public static boolean doesDBExist(ContextWrapper context) {

		File dbFile = context.getDatabasePath("LecturerInfoDB");

		return dbFile.exists();

	}
	
	public int checkIfRosterPopulated() {

		Cursor c = lecturerDatabase.rawQuery("SELECT * FROM "
				+ DATABASE_TABLE_ROSTER, null);

		return c.getCount();

	}
}
