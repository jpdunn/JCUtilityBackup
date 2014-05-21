package au.edu.jcu.it.appframework;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.LecturerDBPopulator;
import au.edu.jcu.it.appframework.model.LecturerInfoDB;
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.ServerCommFacade;
import au.edu.jcu.it.appframework.model.StudentDBPopulator;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class LecturerActivity extends Activity {

	LecturerInfoDB lecturerDB;
	ServerCommFacade serverComm;
	LecturerDBPopulator dbPopulator;
	ListView listView ;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturer);
		lecturerDB = new LecturerInfoDB(this);
		String email = SaveSharedPreference.getUserName(this);
		String password = SaveSharedPreference.getPassword(this);
		serverComm = new ServerCommFacade();
		stabalizeDatabase(email, password);
		
		// 1. pass context and data to the custom adapter
		LecturerSubjectHandler adapter = new LecturerSubjectHandler(this, populateTaughtSubjects());
 
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.lecturerSubjects);
 
        // 3. setListAdapter
        listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lecturer, menu);
		return true;
	}
	
	public void stabalizeDatabase(String email, String password){
		lecturerDB.open();
		if (lecturerDB.checkIfEmailExists(email) == false) {
			ServerCommFacade serverComm = new ServerCommFacade();

			String response = serverComm.getRoster(email);

			LecturerDBPopulator db = new LecturerDBPopulator(this);
			db.addClasses(response);
		} else {

		}

		if (LecturerInfoDB.doesDBExist(this)) {
			System.out.println("DB EXISTS");
			if (lecturerDB.checkIfNotPopulated()) {
				ServerCommFacade serverComm = new ServerCommFacade();

				String response = serverComm.getRoster(email);

				LecturerDBPopulator db = new LecturerDBPopulator(this);
				db.addClasses(response);
			}
		} else {
			System.out.println("DB DOES NOT EXIST");
			lecturerDB = new LecturerInfoDB(this);

			lecturerDB.open();
			ServerCommFacade serverComm = new ServerCommFacade();

			String response = serverComm.getRoster(email);
			System.out.println(response);

			LecturerDBPopulator db = new LecturerDBPopulator(this);
			db.addClasses(response);
			lecturerDB.close();
		}
	}

	public void showTimetable(View view) {
		Intent intent = new Intent(this, Lecturer_Timetable.class);
		startActivity(intent);
	}

	public void manageSubjects(View view) {

		Intent intent = new Intent(this, Lecturer_Manage_Subjects.class);
		startActivity(intent);
	}

	public void Logout(View view) {

		SaveSharedPreference.logout(this);
		finish();
	}


	public JSONArray getTaughtSubjects() {
		JSONArray taughtClassesArray = null;
		try {
			taughtClassesArray = new JSONArray(
					serverComm.currentTeachingSubjects("kim.ku@jcu.edu.au"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return taughtClassesArray;
	}

	
	
	public void viewAttendance(View view){
		Intent intent = new Intent();
		intent.setClass(this, ViewAttendanceActivity.class);
		intent.putExtra("SUBJECT_CODE",view.getTag().toString());
		startActivity(intent);
	}
	
    private ArrayList<LecturerSubjectItem> populateTaughtSubjects(){
        ArrayList<LecturerSubjectItem> items = new ArrayList<LecturerSubjectItem>();
		JSONObject eachSubjectCode;
		JSONArray currentSubjects = getTaughtSubjects();
		String[] subjectsTaught = new String[currentSubjects.length()];
		for (int i = 0; i < currentSubjects.length(); i++) {
			try {
				eachSubjectCode = currentSubjects.getJSONObject(i);
				 items.add(new LecturerSubjectItem(eachSubjectCode.getString("Subject_Code"),"First Item on the list"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return items;
    }
    
    public void attendanceButtonClick(View view){
		Intent intent = new Intent();
		intent.setClass(this, AddNewAttendance.class);
		intent.putExtra("SELECTED_SUBJECT", view.getTag().toString());
		startActivity(intent);
    }
    
    public void rosterButtonClick(View view){
		Intent intent = new Intent();
		intent.setClass(this, ShowEnrolledStudentsActivity.class);
		intent.putExtra("SELECTED_SUBJECT", "CP1030");
		startActivity(intent);
    }

}

