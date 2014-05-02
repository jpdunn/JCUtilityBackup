package au.edu.jcu.it.appframework.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
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
	public final static String KEY_SUBJECT_CLASS_SUBJECT_CODE = "Subject_Code";

	private static final String DATABASE_TABLE_SUBJECTS_SELECTED = "Subjects_Selected";
	public static final String KEY_SUBJECT_SELECTED = "Subject_Selected";
	public static final String KEY_COLOUR = "Colour";
	public static final String KEY_NICKNAME = "Nickname";
	public static final String KEY_PRAC_ID = "Prac_ID";
	public static final String KEY_TUTORIAL_ID = "Tutorial_ID";
	public static final String KEY_WORKSHOP_ID = "Workshop_ID";

	private static final int DATABASE_VERSION = 34	;

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
					+ " (" + KEY_SUBJECT_SELECTED
					+ " TEXT PRIMARY KEY, " + KEY_SUBJECT_ID
					+ " TEXT, " + KEY_COLOUR + " TEXT,"
					+ KEY_NICKNAME + " TEXT," + KEY_PRAC_ID
					+ " INTEGER," + KEY_TUTORIAL_ID
					+ " INTEGER," + KEY_WORKSHOP_ID
					+ " INTEGER" + ");");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
			String subjectRoomString) {

		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_CLASS_SUBJECT_CODE, subjectIDString);
		values.put(KEY_START_TIME, startTimeString);
		values.put(KEY_END_TIME, subjectEndTimeString);
		values.put(KEY_DAY, subjectDayString);
		values.put(KEY_CATEGORY, subjectCategoryString);
		values.put(KEY_LECTURE_ROOM, subjectRoomString);
		return ourDatabase.insert(DATABASE_TABLE_CLASSES, null, values);
	}
	
	public long createEntryEnrolled(String subjectCode){
		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT_SELECTED, subjectCode);
		return ourDatabase.insert(DATABASE_TABLE_SUBJECTS_SELECTED, null, values);
		
	}

	public ArrayList<String> getSubjectCodes() {

		String[] columns = new String[] { KEY_SUBJECT_CODE, KEY_SUBJECT_NAME};

		Cursor c = ourDatabase.query(DATABASE_TABLE_SUBJECTS, columns, null,
				null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();		
		int subID = c.getColumnIndex(KEY_SUBJECT_CODE);
		int subName = c.getColumnIndex(KEY_SUBJECT_NAME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(subID) + " " + c.getString(subName));


		}
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
		return result;
	}
}