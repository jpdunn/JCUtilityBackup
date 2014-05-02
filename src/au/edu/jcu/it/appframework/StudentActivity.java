package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckedTextView;
import au.edu.jcu.it.appframework.model.DBPopulator;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class StudentActivity extends Activity {

	ServerCommFacade serverComm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		serverComm = new ServerCommFacade();

//		 String response = serverComm.getClasses();
//		 String subjectResponse = serverComm.getSubjects();
//		
//		 DBPopulator db = new DBPopulator(this);
//		 db.addSubjects(subjectResponse);
//		 db.addClasses(response);

		CheckedTextView subject1 = (CheckedTextView) findViewById(R.id.EnrolledSubject1);

		CheckedTextView subject2 = (CheckedTextView) findViewById(R.id.EnrolledSubject2);

		CheckedTextView subject3 = (CheckedTextView) findViewById(R.id.EnrolledSubject3);

		CheckedTextView subject4 = (CheckedTextView) findViewById(R.id.EnrolledSubject4);
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student, menu);
		return true;
	}

	public void showTimeTable(View view) {
		Intent timetable = new Intent(this, TimeTableActivity.class);
		startActivity(timetable);
	}

	public void addSubjects(View view) {
		Intent addSubjects = new Intent(this, AddSubjectsActivity.class);
		startActivity(addSubjects);
	}

}
