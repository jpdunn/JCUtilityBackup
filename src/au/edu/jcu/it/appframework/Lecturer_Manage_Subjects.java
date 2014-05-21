package au.edu.jcu.it.appframework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.LecturerInfoDB;
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class Lecturer_Manage_Subjects extends Activity {

	ServerCommFacade serverComms;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturer__manage__subjects);
		serverComms = new ServerCommFacade();
		
		populateClassesDisplay();
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lecturer__manage__subjects, menu);
		return true;
	}
	
	public JSONArray getTaughtSubjects() {
		JSONArray taughtClassesArray = null;
		try {
			taughtClassesArray = new JSONArray(serverComms.currentTeachingSubjects("kim.ku@jcu.edu.au"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return taughtClassesArray;
	}	
	
	public void showEnrolledStudents(View view){
		
		Button selectedSubjectButton = (Button) view;
		String selectedSubject = selectedSubjectButton.getText().toString();
		
		Intent intent = new Intent();
		intent.setClass(this, ShowEnrolledStudentsActivity.class);
		intent.putExtra("SELECTED_SUBJECT", selectedSubject);
		startActivity(intent);
		
	}
	
	private void populateClassesDisplay() {
		JSONObject eachSubjectCode;
		JSONArray currentSubjects = getTaughtSubjects();
		
		for (int i = 0; i < currentSubjects.length(); i++) {
			try {
				eachSubjectCode = currentSubjects.getJSONObject(i);							
				int eachSubjectButtonID = getResources().getIdentifier("manageSubject_" + i,"id", this.getPackageName());
				Button eachButton = (Button) findViewById(eachSubjectButtonID);
				eachButton.setText(eachSubjectCode.getString("Subject_Code"));
				eachButton.setVisibility(View.VISIBLE);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
