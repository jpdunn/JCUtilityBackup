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

public class StudentInfoDB {

	private static final String DATABASE_NAME = "StudentInfoDB";
	// packaged each table up with corresponding fields for reading simplicity
	private static final String DATABASE_TABLE_SUBJECTS = "Subjects";
	public static final String KEY_SUBJECT_ID = "Subject_ID";
	public static final String KEY_SUBJECT_NAME = "Subject_Name";
	public static final String KEY_SUBJECT_CODE = "Subject_Code";

	private static final String DATABASE_TABLE_CLASSES = "Classes";
	public static final String KEY_CLASS_ID = "Class_ID";
	public static final String KEY_DAY = "Day";
	public static final String KEY_START_TIME = "Start_Time";
	public static final String KEY_END_TIME = "End_Time";
	public static final String KEY_LECTURE_ROOM = "Lecture_Room";
	public static final String KEY_CATEGORY = "Category";
	public static final String KEY_SUBJECT_CLASS_SUBJECT_CODE = "Subject_Code";
	public static final String KEY_RAW_START = "Raw_Start";
	public static final String KEY_RAW_END = "Raw_End";
	public static final String KEY_REPEAT = "Repeat";

	private static final String DATABASE_TABLE_SUBJECTS_SELECTED = "Subjects_Selected";
	public static final String KEY_SUBJECT_SELECTED = "Subject_Code";
	public static final String KEY_COLOUR = "Colour";
	public static final String KEY_NICKNAME = "Nickname";
	public static final String KEY_PRAC_ID = "Prac_ID";
	public static final String KEY_TUTORIAL_ID = "Tutorial_ID";
	public static final String KEY_WORKSHOP_ID = "Workshop_ID";
	
	private static final String DATABASE_TABLE_USERS = "Users_Logged_In";
	public static final String KEY_EMAIL = "Email";
	public static final String KEY_PASSWORD = "Password";

	private static final int DATABASE_VERSION = 63;

	ArrayList<String> filter = new ArrayList<String>();

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTables(db);
		}

		public void createTables(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_SUBJECTS + " ("
					+ KEY_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_SUBJECT_NAME + " TEXT, " + KEY_SUBJECT_CODE
					+ " TEXT);");

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_CLASSES + " ("
					+ KEY_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_SUBJECT_ID + " INTEGER, " + KEY_DAY + " TEXT, "
					+ KEY_START_TIME + " REAL, " + KEY_END_TIME + " REAL,"
					+ KEY_LECTURE_ROOM + " TEXT, " + KEY_CATEGORY + " TEXT, "
					+ KEY_SUBJECT_CLASS_SUBJECT_CODE + " TEXT, "
					+ KEY_RAW_START + " TEXT, " + KEY_RAW_END + " TEXT, "
					+ KEY_REPEAT + " TEXT);");

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_SUBJECTS_SELECTED
					+ " (" + KEY_SUBJECT_SELECTED
					+ " TEXT PRIMARY KEY UNIQUE, " + KEY_SUBJECT_ID + " TEXT, "
					+ KEY_COLOUR + " TEXT," + KEY_NICKNAME + " TEXT,"
					+ KEY_PRAC_ID + " INTEGER," + KEY_TUTORIAL_ID + " INTEGER,"
					+ KEY_WORKSHOP_ID + " INTEGER" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			dropTables(db);

		}

		public void dropTables(SQLiteDatabase db) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLASSES);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SUBJECTS);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DATABASE_TABLE_SUBJECTS_SELECTED);
			onCreate(db);

		}
	}

	public StudentInfoDB(Context c) {
		ourContext = c;
	}

	public StudentInfoDB open() throws SQLException {

		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();

		return this;
	}

	public void close() {

		ourHelper.close();
	}

	public long createEntrySubjects(String subjectCodeString,
			String subjectNameString) {

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CODE, subjectCodeString);
		values.put(KEY_SUBJECT_NAME, subjectNameString);
		return ourDatabase.insert(DATABASE_TABLE_SUBJECTS, null, values);
	}

	public long createEntryClasses(String subjectIDString,
			String startTimeString, String subjectEndTimeString,
			String subjectDayString, String subjectCategoryString,
			String subjectRoomString, String rawStartString,
			String rawEndString, String repeatString) {

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CLASS_SUBJECT_CODE, subjectIDString);
		values.put(KEY_START_TIME, startTimeString);
		values.put(KEY_END_TIME, subjectEndTimeString);
		values.put(KEY_DAY, subjectDayString);
		values.put(KEY_CATEGORY, subjectCategoryString);
		values.put(KEY_LECTURE_ROOM, subjectRoomString);
		values.put(KEY_RAW_START, rawStartString);
		values.put(KEY_RAW_END, rawEndString);
		values.put(KEY_REPEAT, repeatString);

		return ourDatabase.insert(DATABASE_TABLE_CLASSES, null, values);
	}

	public long createEntryEnrolled(String subjectCode, int subjectIndex) {

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_SELECTED, subjectCode);
		values.put(KEY_COLOUR, DefaultColoursEnum.values()[subjectIndex].toString());
		return ourDatabase.insert(DATABASE_TABLE_SUBJECTS_SELECTED, null,
				values);

	}

	public ArrayList<String> getEnrolledSubjects() {
		open();

		String[] columns = new String[] { KEY_SUBJECT_SELECTED };

		Cursor c = ourDatabase.query(DATABASE_TABLE_SUBJECTS_SELECTED, columns,
				null, null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();
		int subjectSelected = c.getColumnIndex(KEY_SUBJECT_SELECTED);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(subjectSelected) + " \n");

		}
		close();
		return result;
	}

	public ArrayList<String> getSubjectCodes() {

		String[] columns = new String[] { KEY_SUBJECT_CODE, KEY_SUBJECT_NAME };

		Cursor c = ourDatabase.query(DATABASE_TABLE_SUBJECTS, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();
		int subID = c.getColumnIndex(KEY_SUBJECT_CODE);
		int subName = c.getColumnIndex(KEY_SUBJECT_NAME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(subID) + " " + c.getString(subName));

		}
		close();
		return result;

	}

	public String getSubjectNames() {

		String[] columns = new String[] { KEY_SUBJECT_NAME };

		Cursor c = ourDatabase.query(DATABASE_TABLE_SUBJECTS, columns, null,
				null, null, null, null);
		String result = "";
		int subName = c.getColumnIndex(KEY_SUBJECT_NAME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(subName) + " " + "\n";

		}
		close();
		return result;
	}

	public String getRowID() {

		String[] columns = new String[] { KEY_SUBJECT_ID };

		Cursor c = ourDatabase.query(DATABASE_TABLE_SUBJECTS, columns, null,
				null, null, null, null);
		String result = "";
		int rowID = c.getColumnIndex(KEY_SUBJECT_ID);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(rowID) + " " + "\n";

		}
		close();
		return result;
	}

	public ArrayList<String> getAllSubjectCodesForClasses() {

		String[] columns = new String[] { KEY_SUBJECT_CLASS_SUBJECT_CODE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();
		int subCode = c.getColumnIndex(KEY_SUBJECT_CLASS_SUBJECT_CODE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result.add(c.getString(subCode));

		}
		close();
		return result;
	}

	public ArrayList<String> getAllStartTimesForClasses() {

		String[] columns = new String[] { KEY_START_TIME };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();
		int startTime = c.getColumnIndex(KEY_START_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(startTime));
		}
		close();
		return result;
	}

	public ArrayList<String> getAllEndTimesForClasses() {

		String[] columns = new String[] { KEY_END_TIME };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		int endTime = c.getColumnIndex(KEY_END_TIME);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result.add(c.getString(endTime));
		}
		close();
		return result;
	}

	public ArrayList<String> getAllDayForClasses() {

		String[] columns = new String[] { KEY_DAY };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		int day = c.getColumnIndex(KEY_DAY);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result.add(c.getString(day));
		}
		close();
		return result;
	}

	public ArrayList<String> getAllCategoryForClasses() {

		String[] columns = new String[] { KEY_CATEGORY };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		int category = c.getColumnIndex(KEY_CATEGORY);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result.add(c.getString(category));
		}
		close();
		return result;
	}

	public ArrayList<String> getAllRoomsForClasses() {

		String[] columns = new String[] { KEY_LECTURE_ROOM };

		Cursor c = ourDatabase.query(DATABASE_TABLE_CLASSES, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		int lectureRoom = c.getColumnIndex(KEY_LECTURE_ROOM);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result.add(c.getString(lectureRoom));
		}
		close();
		return result;
	}

	public static boolean doesDBExist(ContextWrapper context) {

		File dbFile = context.getDatabasePath("StudentInfoDB");

		return dbFile.exists();

	}

	public void deleteUser(String subject) {
		open();
		ourDatabase.delete(DATABASE_TABLE_SUBJECTS_SELECTED, KEY_SUBJECT_CODE
				+ "=?", new String[] { subject });
		close();
	}

	public boolean checkIfNotPopulated() {

		Cursor c = ourDatabase.rawQuery("SELECT * FROM "
				+ DATABASE_TABLE_SUBJECTS, null);

		int query = c.getCount();
		if (query == 0) {
			return true;

		}
		return false;

	}

	public int getNumberOfEnrolledSubjects() {
		Cursor c = ourDatabase.rawQuery("SELECT * FROM "
				+ DATABASE_TABLE_SUBJECTS_SELECTED, null);

		return c.getCount();
	}

	public ArrayList<Map> getEnrolledSubjectsInfo() {

		String query = "SELECT * FROM Subjects_Selected JOIN Subjects"
				+ " ON Subjects_Selected.Subject_Code=Subjects.Subject_Code";
		Cursor c = ourDatabase.rawQuery(query, null);
		ArrayList<Map> allResults = new ArrayList<Map>();
		int subjectCode = c.getColumnIndex(KEY_SUBJECT_CODE);
		int subjectName = c.getColumnIndex(KEY_SUBJECT_NAME);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Map<String, String> eachResult = new HashMap<String, String>();

			eachResult.put("Subject_Code", c.getString(subjectCode));
			eachResult.put("Subject_Name", c.getString(subjectName));
			allResults.add(eachResult);
		}
		return allResults;
	}
	
	public void setNewColourForSubject(String subjectCode, String colour){
		open();
		String query = "UPDATE Subjects_Selected SET Colour='" + colour + "' WHERE Subject_Code= '" + subjectCode + "' ;";
		ourDatabase.execSQL(query);
		close();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Map> getEnrolledClasses(int dayValue) {

		
		String query = "SELECT * FROM Subjects JOIN Classes "
				+ "ON Subjects.Subject_Code =Classes.Subject_Code "
				+ "JOIN Subjects_Selected "
				+ "ON Subjects.Subject_Code = Subjects_Selected.Subject_Code "
				+ "WHERE Day = " + dayValue;
		Cursor c = ourDatabase.rawQuery(query, null);
		ArrayList<Map> allClasses = new ArrayList<Map>();
		int startTime = c.getColumnIndex(KEY_START_TIME);
		int endTime = c.getColumnIndex(KEY_END_TIME);
		int day = c.getColumnIndex(KEY_DAY);
		int rawStart = c.getColumnIndex(KEY_RAW_START);
		int rawEnd = c.getColumnIndex(KEY_RAW_END);
		int lectureRoom = c.getColumnIndex(KEY_LECTURE_ROOM);
		int repeat = c.getColumnIndex(KEY_REPEAT);
		int category = c.getColumnIndex(KEY_CATEGORY);
		int subName = c.getColumnIndex(KEY_SUBJECT_NAME);
		int subjectColor = c.getColumnIndex(KEY_COLOUR);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Map<String, String> eachResult = new HashMap<String, String>();
			eachResult.put("Start_Time", c.getString(startTime));
			eachResult.put("End_Time", c.getString(endTime));
			eachResult.put("Day", c.getString(day));
			eachResult.put("Raw_Start", c.getString(rawStart));
			eachResult.put("Raw_End", c.getString(rawEnd));
			eachResult.put("Lecture_Room", c.getString(lectureRoom));
			eachResult.put("Repeat", c.getString(repeat));
			eachResult.put("Category", c.getString(category));
			eachResult.put("Subject_Name", c.getString(subName));
			eachResult.put("Colour", c.getString(subjectColor));
			allClasses.add(eachResult);
		}
		return allClasses;
	}

	public long createEntryUser(String email, String password){
		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_PASSWORD, password);
		return ourDatabase.insert(DATABASE_TABLE_USERS, null, values);
	}
}