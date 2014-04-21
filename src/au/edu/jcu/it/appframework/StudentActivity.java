package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StudentActivity extends Activity {


	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student, menu);
		return true;
	}


	
	
	public void showTimeTable(View view){
		
		Intent timetable = new Intent(this, TimeTableActivity.class);
		startActivity(timetable);
		
	}
	
	public void addSubjects(View view){
		
		Intent addSubjects = new Intent(this, AddSubjectsActivity.class);
		startActivity(addSubjects);
	}
	
	
	
	

}
