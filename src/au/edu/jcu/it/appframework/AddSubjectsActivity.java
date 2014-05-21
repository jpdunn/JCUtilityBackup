package au.edu.jcu.it.appframework;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.ServerCommFacade;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class AddSubjectsActivity extends Activity implements TextWatcher {
	StudentInfoDB subjects;
	AutoCompleteTextView subjectNameAutoComplete;
	ServerCommFacade serverComms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subjects);
		serverComms = new ServerCommFacade();
		subjectNameAutoComplete = (AutoCompleteTextView) findViewById(R.id.SubjectNameAutoComplete);
		StudentInfoDB subjects = new StudentInfoDB(this);
		subjects.open();
		ArrayList<String> allDays = subjects.getSubjectCodes();
		String allSubjects[] = allDays.toArray(new String[allDays.size()]);
		subjects.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, allSubjects);
		subjectNameAutoComplete.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_subjects, menu);
		return true;

	}

	@SuppressLint("DefaultLocale")
	public void addSubjectsSelected(View view) {

		StudentInfoDB subjects = new StudentInfoDB(this);
		subjects.open();

		if (subjects.getNumberOfEnrolledSubjects() == 4) {
			Toast.makeText(this, "You can only enroll in a maximum of 4 subjects.", Toast.LENGTH_LONG).show();
		}else{
			String subjectSelected = subjectNameAutoComplete.getText().toString();

			String subjectCode = subjectSelected.split(" ")[0];
			subjects.createEntryEnrolled(subjectCode, subjects.getNumberOfEnrolledSubjects());
			subjects.close();
			serverComms.enrolStudentInClass(subjectCode.toUpperCase(), SaveSharedPreference.getUserName(this));

			Toast.makeText(this, "Subject Added: " + subjectCode, Toast.LENGTH_LONG).show();

			
		}
		finish();
	}

	@Override
	public void afterTextChanged(Editable s) {
		// UNEEDED METHOD
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// UNEEDED METHOD
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// UNEEDED METHOD

	}

}
