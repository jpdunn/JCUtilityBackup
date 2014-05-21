package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class AddNewAttendance extends Activity {

	String selectedSubject = null;
	ServerCommFacade serverComms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		serverComms = new ServerCommFacade();
		setContentView(R.layout.activity_add_new_attendance);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String potentialSelectedSubject = extras
					.getString("SELECTED_SUBJECT");
			if (potentialSelectedSubject != null) {
				selectedSubject = potentialSelectedSubject;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_new_attendance, menu);
		return true;
	}
	
	public void generateAtendanceCode(View view){
		TextView attendanceCodeNotesText = (TextView) findViewById(R.id.attendanceCodeNotesText);
		String response = serverComms.createNewAttendanceCode(
				attendanceCodeNotesText.getText().toString(), 
				selectedSubject, 
				"kim.ku@jcu.edu.au");
		
		System.out.println(response);
		if(response.equals("OK")){
			Context context = getApplicationContext();
			CharSequence text = "An email link has been sent for attendance code";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			finish();
		}
	}
}
