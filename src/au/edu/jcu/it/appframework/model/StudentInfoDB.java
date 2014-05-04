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
import android.database.sqlite.SQLiteException;
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
	public final static String KEY_SUBJECT_CLASS_SUBJECT_CODE = "Subject_Code";

	private static final String DATABASE_TABLE_SUBJECTS_SELECTED = "Subjects_Selected";
	public static final String KEY_SUBJECT_SELECTED = "Subject_Code";
	public static final String KEY_COLOUR = "Colour";
	public static final String KEY_NICKNAME = "Nickname";
	public static final String KEY_PRAC_ID = "Prac_ID";
	public static final String KEY_TUTORIAL_ID = "Tutorial_ID";
	public static final String KEY_WORKSHOP_ID = "Workshop_ID";

	private static final int DATABASE_VERSION = 44;

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
					+ KEY_SUBJECT_CLASS_SUBJECT_CODE + " TEXT " + " );");

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_SUBJECTS_SELECTED
					+ " (" + KEY_SUBJECT_SELECTED + " TEXT PRIMARY KEY, "
					+ KEY_SUBJECT_ID + " TEXT, " + KEY_COLOUR + " TEXT,"
					+ KEY_NICKNAME + " TEXT," + KEY_PRAC_ID + " INTEGER,"
					+ KEY_TUTORIAL_ID + " INTEGER," + KEY_WORKSHOP_ID
					+ " INTEGER" + ");");

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

		open();
		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CODE, subjectCodeString);
		values.put(KEY_SUBJECT_NAME, subjectNameString);
		return ourDatabase.insert(DATABASE_TABLE_SUBJECTS, null, values);
	}

	public long createEntryClasses(String subjectIDString,
			String startTimeString, String subjectEndTimeString,
			String subjectDayString, String subjectCategoryString,
			String subjectRoomString) {

		open();

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CLASS_SUBJECT_CODE, subjectIDString);
		values.put(KEY_START_TIME, startTimeString);
		values.put(KEY_END_TIME, subjectEndTimeString);
		values.put(KEY_DAY, subjectDayString);
		values.put(KEY_CATEGORY, subjectCategoryString);
		values.put(KEY_LECTURE_ROOM, subjectRoomString);
		return ourDatabase.insert(DATABASE_TABLE_CLASSES, null, values);
	}

	public long createEntryEnrolled(String subjectCode) {

		open();
		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_SELECTED, subjectCode);
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

		open();
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

		open();

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

		open();
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

		open();

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

		open();

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
		open();

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

		open();

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
		open();

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

		open();
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

	public boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = "/data/data/au.edu.jcu.it.appframework/databases/StudentInfoDB";
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);

		} catch (SQLiteException e) {

			// database does't exist yet.

			System.out.println("DB Doesn't exist");

		}

		if (checkDB != null) {

			checkDB.close();
			System.out.println("DB Does exist");

		}

		return checkDB != null ? true : false;
	}

	public static boolean doesDBExist(ContextWrapper context) {

		File dbFile = context.getDatabasePath("StudentInfoDB");

		return dbFile.exists();

	}

	public void deleteUser(String subject) {
		try {
			ourDatabase.delete(DATABASE_TABLE_SUBJECTS_SELECTED,
					"Subject_Code = ?", new String[] { KEY_SUBJECT_SELECTED });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ourDatabase.close();
		}
	}

	public boolean checkIfNotPopulated() {

		open();
		Cursor c = ourDatabase.rawQuery("SELECT * FROM "
				+ DATABASE_TABLE_SUBJECTS, null);

		System.out.println(c.getCount());

		int query = c.getCount();
		if (query == 0) {
			return true;

		}
		return false;

	}

	public ArrayList<Map> getEnrolledSubjectsInfo() {

		String query = "SELECT * FROM Subjects_Selected JOIN Subjects ON "
				+ "Subjects_Selected.Subject_Code = Subjects.Subject_Code";
		Cursor c = ourDatabase.rawQuery(query, null);
		ArrayList<Map> allResults = new ArrayList<Map>();
		int subjectCode = c.getColumnIndex(KEY_SUBJECT_CODE);
		int subjectName = c.getColumnIndex(KEY_SUBJECT_NAME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Map<String, String> eachResult = new HashMap<String, String>();

			eachResult.put("Subject Code", c.getString(subjectCode));
			eachResult.put("Subject Name", c.getString(subjectName));
			allResults.add(eachResult);
		}
		// used for error checking
//		for (Map map : allResults) {
//			System.out.println(map.get("Subject Code"));
//			System.out.println(map.get("Subject Name"));
//		}

		return allResults;

	}
}