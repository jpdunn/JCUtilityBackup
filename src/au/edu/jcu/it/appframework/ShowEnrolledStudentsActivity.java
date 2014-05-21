package au.edu.jcu.it.appframework;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class ShowEnrolledStudentsActivity extends Activity {
	
	String selectedSubjectCode;
	ServerCommFacade serverComms;
	JSONArray taughtClassesArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_enrolled_students);
		
		serverComms = new ServerCommFacade();
		
		  Bundle extras = getIntent().getExtras();
		  if (extras != null) {
		   String selectedSubjectCode = extras.getString("SELECTED_SUBJECT");
		   if (selectedSubjectCode!= null) {
			   
			   try {
					taughtClassesArray = new JSONArray(serverComms.getEnrolledStudentsInSubject(selectedSubjectCode));
					displayStudentsInSubject();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		   }     
		  }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_enrolled_subjects, menu);
		return true;
	}
	
	private void displayStudentsInSubject(){
		TextView studentsInSubjectDisplay = (TextView) findViewById(R.id.enrolledStudentsDisplay);
		
		
		String studentsString = "";
		
		for (int i = 0; i < taughtClassesArray.length(); i++) {
			try {
				String eachStudentString  = 
				StringsHandler.convertEmail(taughtClassesArray.getJSONObject(i).getString("student_email").split("@")[0]);
				//String eachFirstName =  eachStudentString.split(".")[0];
				//String eachSurname =  eachStudentString.split(".")[1];
				
				studentsString += eachStudentString + "\r\n";
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		studentsInSubjectDisplay.setText(studentsString);
		
	}
}
