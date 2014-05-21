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
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.StudentDBPopulator;
import au.edu.jcu.it.appframework.model.ServerCommFacade;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class StudentActivity extends Activity {

	ServerCommFacade serverComm;
	StudentInfoDB enrolledSubjects;
	String subject;
	TextView thisSubject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		enrolledSubjects = new StudentInfoDB(this);
		enrolledSubjects.open();
		String email = SaveSharedPreference.getUserName(this);
		String password = SaveSharedPreference.getPassword(this);
		
		enrolledSubjects.createEntryUser(email, password);
		
		if (StudentInfoDB.doesDBExist(this)) {
			System.out.println("DB EXISTS");
			if (enrolledSubjects.checkIfNotPopulated()) {
				ServerCommFacade serverComm = new ServerCommFacade();

				String response = serverComm.getClasses();
				String subjectResponse = serverComm.getSubjects();

				StudentDBPopulator db = new StudentDBPopulator(this);
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

			StudentDBPopulator db = new StudentDBPopulator(this);
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

	public void populateSubjects() {
		enrolledSubjects.open();
		ArrayList<Map> eachSubject = enrolledSubjects.getEnrolledSubjectsInfo();
		if (eachSubject != null) {

			for (int i = 0; i < eachSubject.size(); i++) {

				int id = getResources().getIdentifier("EnrolledSubject" + i,
						"id", this.getPackageName());
				TextView eachTextView = (TextView) findViewById(id);
				eachTextView.setText((CharSequence) eachSubject.get(i).get("Subject_Code") + ": " + eachSubject.get(i).get("Subject_Name"));

				int rowID = getResources().getIdentifier("subjectRow" + i,
						"id", this.getPackageName());
				TableRow eachTableRow = (TableRow) findViewById(rowID);
				eachTableRow.setVisibility(View.VISIBLE);
			}
		}
		enrolledSubjects.close();
	}

	public void removeSubject(View view) {

		switch (view.getId()) {
		case R.id.removeSubject1:
			TableRow subject1 = (TableRow) findViewById(R.id.subjectRow0);
			thisSubject = (TextView) findViewById(R.id.EnrolledSubject0);
			subject = (String) thisSubject.getText();
			subject = subject.split(":")[0];
			enrolledSubjects.deleteUser(subject);
			System.out.println(subject + "Has been deleted");
			subject1.setVisibility(View.GONE);
			break;
		case R.id.removeSubject2:
			TableRow subject2 = (TableRow) findViewById(R.id.subjectRow1);
			thisSubject = (TextView) findViewById(R.id.EnrolledSubject1);
			subject = (String) thisSubject.getText();
			subject = subject.split(":")[0];
			enrolledSubjects.deleteUser(subject);
			System.out.println(subject + "Has been deleted");
			subject2.setVisibility(View.GONE);
			break;
		case R.id.removeSubject3:
			TableRow subject3 = (TableRow) findViewById(R.id.subjectRow2);
			thisSubject = (TextView) findViewById(R.id.EnrolledSubject2);
			subject = (String) thisSubject.getText();
			subject = subject.split(":")[0];
			enrolledSubjects.deleteUser(subject);
			System.out.println(subject + "Has been deleted");
			subject3.setVisibility(View.GONE);
			break;
		case R.id.removeSubject4:
			TableRow subject4 = (TableRow) findViewById(R.id.subjectRow3);
			thisSubject = (TextView) findViewById(R.id.EnrolledSubject3);
			subject = (String) thisSubject.getText();
			subject = subject.split(":")[0];
			enrolledSubjects.deleteUser(subject);
			System.out.println(subject + "Has been deleted");
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
		Intent timetable = new Intent(this, StudentTimeTableActivity.class);
		startActivity(timetable);
	}

	public void addSubjects(View view) {
		Intent addSubjects = new Intent(this, AddSubjectsActivity.class);
		startActivity(addSubjects);
	}
	
	public void modifySubject(View view){
		TextView selectedSubjectView = null;

		switch (view.getId()) {
		case R.id.modifySubject1:
			selectedSubjectView = (TextView) findViewById(R.id.EnrolledSubject0);
			break;
		case R.id.modifySubject2:
			selectedSubjectView = (TextView) findViewById(R.id.EnrolledSubject1);
			break;
		case R.id.modifySubject3:
			selectedSubjectView = (TextView) findViewById(R.id.EnrolledSubject2);
			break;
		case R.id.modifySubject4:
			selectedSubjectView = (TextView) findViewById(R.id.EnrolledSubject3);
			break;
		}

		String subjectString = (String) (selectedSubjectView.getText());
		
		Intent intent = new Intent();
		intent.setClass(this, ModifySubjectSettings.class);
		
		intent.putExtra("SUBJECT_CODE",subjectString.split(":")[0] );
		startActivity(intent);
	}

	
	public void attendClass(View view){
		Intent intent = new Intent();
		intent.setClass(this, StudentAttendClass.class);
		startActivity(intent);
	}
	
	public void Logout(View view){
		SaveSharedPreference.logout(this);
		finish();
	}
	
}
