package au.edu.jcu.it.appframework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class ViewSingleAttendanceActivity extends Activity {
	
	public ListView attendanceDetailsList;
	ServerCommFacade serverComms;
	String selectedCode = null;
	JSONArray attendanceCodes = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_attendance);
		serverComms = new ServerCommFacade();
		
		
		Bundle extras = getIntent().getExtras();
		  if (extras != null) {
		   String datas= extras.getString("SUBJECT_SELECTED");
		   if (datas!= null) {
			   selectedCode = datas;
		   }    
		  }
		String attendanceRecords = serverComms.getStudentAttendanceForRecord(selectedCode);
		attendanceDetailsList = (ListView) findViewById(R.id.attendanceDetailsList);
		
		
		try {
			attendanceCodes = new JSONArray(attendanceRecords);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		final String[] values = new String[attendanceCodes.length()];
		try {
			for (int i = 0; i < attendanceCodes.length(); i++) {
				JSONObject eachCode = attendanceCodes.getJSONObject(i);
				values[i] = eachCode.getString("Student_Email");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values );
		attendanceDetailsList.setAdapter(adapter); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_single_attendance, menu);
		return true;
	}

}
