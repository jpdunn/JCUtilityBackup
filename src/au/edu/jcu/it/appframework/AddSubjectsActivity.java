package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class AddSubjectsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subjects);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_subjects, menu);
		return true;
	}
	
	
	public void addSubject(View view){
		
			Toast.makeText(this, "Subjects Added",
					   Toast.LENGTH_LONG).show();

		finish();
	}

}
