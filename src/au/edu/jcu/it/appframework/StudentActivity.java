package au.edu.jcu.it.appframework;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.DBPopulator;
import au.edu.jcu.it.appframework.model.ServerCommFacade;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class StudentActivity extends Activity {

	ServerCommFacade serverComm;
	StudentInfoDB enrolledSubjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		enrolledSubjects = new StudentInfoDB(this);
		enrolledSubjects.open();
		System.out.println(enrolledSubjects.getEnrolledSubjectsInfo()
				.toString());

		if (StudentInfoDB.doesDBExist(this)) {
			System.out.println("DB EXISTS");
			if (enrolledSubjects.checkIfNotPopulated()) {
				ServerCommFacade serverComm = new ServerCommFacade();

				String response = serverComm.getClasses();
				String subjectResponse = serverComm.getSubjects();
				System.out.println(response);

				DBPopulator db = new DBPopulator(this);
				db.addSubjects(subjectResponse);
				db.addClasses(response);
			}
		} else {
			System.out.println("DB DOES NOT EXIST");
			enrolledSubjects = new StudentInfoDB(this);

			enrolledSubjects.open();
			ServerCommFacade serverComm = new ServerCommFacade();

			String response = serverComm.getClasses();
			String subjectResponse = serverComm.getSubjects();
			System.out.println(response);

			DBPopulator db = new DBPopulator(this);
			db.addSubjects(subjectResponse);
			db.addClasses(response);
			enrolledSubjects.close();
		}

		populateSubjects();

	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		populateSubjects();
	}
	
	public void populateSubjects(){
		ArrayList<Map> eachSubject = enrolledSubjects.getEnrolledSubjectsInfo();
		if (eachSubject != null) {

			for (int i = 0; i < eachSubject.size(); i++) {
				
				int id = getResources().getIdentifier("EnrolledSubject" + i,
						"id", this.getPackageName());
				TextView eachTextView = (TextView) findViewById(id);
				eachTextView.setText((CharSequence) eachSubject.get(i).get(
						"Subject Code") + " " + eachSubject.get(i).get("Subject Name"));

				int rowID = getResources().getIdentifier("subjectRow" + i,
						"id", this.getPackageName());
				TableRow eachTableRow = (TableRow) findViewById(rowID);
				eachTableRow.setVisibility(View.VISIBLE);
			}
		}
	}

	public void removeSubject(View view) {

		switch (view.getId()) {
		case R.id.removeSubject1:
			System.out.println("subject 1");
			TableRow subject1 = (TableRow) findViewById(R.id.subjectRow0);
			subject1.setVisibility(View.GONE);
			break;
		case R.id.removeSubject2:
			TableRow subject2 = (TableRow) findViewById(R.id.subjectRow1);
			subject2.setVisibility(View.GONE);
			break;
		case R.id.removeSubject3:
			TableRow subject3 = (TableRow) findViewById(R.id.subjectRow2);
			subject3.setVisibility(View.GONE);
			break;
		case R.id.removeSubject4:
			TableRow subject4 = (TableRow) findViewById(R.id.subjectRow3);
			subject4.setVisibility(View.GONE);
			break;
		}
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
