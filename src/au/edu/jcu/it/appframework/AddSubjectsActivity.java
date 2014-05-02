package au.edu.jcu.it.appframework;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class AddSubjectsActivity extends Activity implements TextWatcher {
	StudentInfoDB subjects;
	AutoCompleteTextView subjectNameAutoComplete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subjects);

		subjectNameAutoComplete = (AutoCompleteTextView) findViewById(R.id.SubjectNameAutoComplete);

		StudentInfoDB subjects = new StudentInfoDB(this);

		
		subjects.open();

		
		ArrayList<String> allDays = subjects.getSubjectCodes();
		
		String allSubjects[] = allDays.toArray(new String[allDays.size()]);
		
		
		subjects.close();
//
//		subjectNameAutoComplete.addTextChangedListener(this);
//		subjectNameAutoComplete.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line, allDays));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, allSubjects);
        subjectNameAutoComplete.setAdapter(adapter);
        
        	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_subjects, menu);
		return true;

	}
	
	

	public void addSubjectsSelected(View view) {

		StudentInfoDB subjects = new StudentInfoDB(this);

		String subjectSelected = subjectNameAutoComplete.getText().toString();

		String subjectCode = subjectSelected.split(" ")[0];
		String subjectName = subjectSelected.split(" ")[1];
        System.out.println(subjectCode + " " + subjectName);
        subjects.open();
        subjects.createEntryEnrolled(subjectCode);
        subjects.close();

		Toast.makeText(this, "Subjects Added", Toast.LENGTH_LONG).show();

		finish();
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

}
