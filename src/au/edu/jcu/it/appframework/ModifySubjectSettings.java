package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class ModifySubjectSettings extends Activity {
	String subjectCodeSelected;
	StudentInfoDB studentInfoDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_subject_settings);
		studentInfoDB = new StudentInfoDB(this);

		Bundle bundleFromStudentActivity = getIntent().getExtras();
		if (bundleFromStudentActivity != null) {
		   subjectCodeSelected = bundleFromStudentActivity.getString("SUBJECT_CODE");
			if (bundleFromStudentActivity != null) {

				TextView title = (TextView) findViewById(R.id.colourSelectionTitle);
				title.setText("Please Select Your Colour for " + subjectCodeSelected);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_subject_settings, menu);
		return true;
	}
	
	public void changeColour (View view){
		RadioGroup selectedColour = (RadioGroup) findViewById(R.id.colourPickerRadioGroup);
		int selectedID = selectedColour.getCheckedRadioButtonId();
		RadioButton colourSelected = (RadioButton) findViewById(selectedID);
		
		String selectedColourText = (String) (colourSelected.getText());
		
		studentInfoDB.setNewColourForSubject(subjectCodeSelected, selectedColourText);
		
		finish();
		
	}

}
