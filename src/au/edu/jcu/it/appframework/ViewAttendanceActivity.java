package au.edu.jcu.it.appframework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.LecturerInfoDB;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class ViewAttendanceActivity extends Activity {

	private String selectedSubjectCode;
	ServerCommFacade serverComms;
	ListView listView;
	JSONArray attendanceCodes = null;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_view_attendance);
		
		serverComms = new ServerCommFacade();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String datas = extras.getString("SUBJECT_CODE");
			if (datas != null) {
				selectedSubjectCode = datas;
			}
		}
        listView = (ListView) findViewById(R.id.attendanceList);
		String attendanceCodeForLecturer = serverComms.getLecturerAttendanceCodes("kim.ku@jcu.edu.au", selectedSubjectCode);
		
		try {
			attendanceCodes = new JSONArray(attendanceCodeForLecturer);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		final String[] values = new String[attendanceCodes.length()];
		final String[] ids = new String[attendanceCodes.length()];
		try {
			for (int i = 0; i < attendanceCodes.length(); i++) {
				JSONObject eachCode = attendanceCodes.getJSONObject(i);
				values[i] = eachCode.getString("Attendance_Code_Note");
				ids[i] = eachCode.getString("Attendance_Code_ID");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter); 
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent();
				intent.setClass(context, ViewSingleAttendanceActivity.class);
				intent.putExtra("SUBJECT_SELECTED", ids[position]);
				startActivity(intent);
			}
        	
        }
		);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_attendance, menu);
		return true;
	}

}
