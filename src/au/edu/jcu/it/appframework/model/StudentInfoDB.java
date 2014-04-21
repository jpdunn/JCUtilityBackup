package au.edu.jcu.it.appframework.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentInfoDB {

	private static final String DATABASE_NAME = "StudentInfoDB";
	
	//packaged each table up with corresponding fields for reading simplicity
	private static final String DATABASE_TABLE_SUBJECTS = "Subjects";
	public static final String KEY_SUBJECT_ID = "Subject_ID";
	public static final String KEY_SUBJECT_NAME = "Subject_Name";
	public static final String KEY_SUBJECT_CODE = "Subject_Code";
	
	private static final String DATABASE_TABLE_CLASSES = "Classes";
	public static final String KEY_CLASS_ID = "Class_ID";
	public static final String KEY_DAY = "Day";
	public static final String KEY_START_TIME = "Start_Time";
	public static final String KEY_END_TIME = "End_Time";
	public static final String KEY_ROOM_ID = "Room_ID";
	public static final String KEY_CLASS_TYPE = "Class_Type";
	
	private static final String DATABASE_TABLE_SUBJECTS_SELECTED = "Subjects_Selected";
	public static final String KEY_SUBJECT_SELECTED = "Subject_Selected";
	public static final String KEY_COLOUR = "Colour";
	public static final String KEY_NICKNAME = "Nickname";
	public static final String KEY_PRAC_ID = "Prac_ID";
	public static final String KEY_TUTORIAL_ID = "Tutorial_ID";
	public static final String KEY_WORKSHOP_ID = "Workshop_ID";

	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_SUBJECTS + " (" + KEY_SUBJECT_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SUBJECT_NAME
					+ " TEXT NOT NULL, " + KEY_SUBJECT_CODE + " TEXT NOT NULL);");
			
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_CLASSES + " (" + KEY_CLASS_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SUBJECT_ID
					+ " INTEGER NOT NULL, " + KEY_DAY + " TEXT NOT NULL," + KEY_START_TIME 
					+ " REAL NOT NULL," + KEY_END_TIME + " REAL NOT NULL," + KEY_ROOM_ID 
					+ " TEXT NOT NULL," + KEY_CLASS_TYPE + " TEXT NOT NULL" + " );");
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_SUBJECTS_SELECTED + " (" + KEY_SUBJECT_SELECTED
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SUBJECT_ID
					+ " INTEGER NOT NULL, " + KEY_COLOUR + " TEXT NOT NULL," 
					+ KEY_NICKNAME + " TEXT NOT NULL," + KEY_PRAC_ID + " INTEGER NOT NULL,"
					+ KEY_TUTORIAL_ID + " INTEGER NOT NULL," + KEY_WORKSHOP_ID + " INTEGER NOT NULL" + ");");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLASSES + ", " 
			+ DATABASE_TABLE_SUBJECTS + ", " + DATABASE_TABLE_SUBJECTS_SELECTED);
			onCreate(db);
			
		}

		

	}
	
	
	public StudentInfoDB(Context c) {
		ourContext = c;
	}
	

	
	
	public StudentInfoDB open() throws SQLException{
		
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getReadableDatabase();
		
		return this;
	}
	
}
