package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.LecturerInfoDB;
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class MainActivity extends Activity {

	private EditText username, password;
	private String usernameText, passwordText;
	private Button loginButton;
	private static final String TAG = MainActivity.class.getName();
	int attemptCount = 3;
	LecturerInfoDB lecturerDB;
	StudentInfoDB studentDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkUserLoggedIn();
		getActionBar().hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void checkUserLoggedIn(){

		if(!SaveSharedPreference.getUserName(this).toString().isEmpty()){
			
			if(SaveSharedPreference.getUserType(this) == "Lecturer"){
				Intent nextActivity = new Intent(this, StudentActivity.class);
				startActivity(nextActivity);
				
			}else{
				Intent nextActivity = new Intent(this, LecturerActivity.class);
				startActivity(nextActivity);
			}
		}
	}

	public void register(View view) {

		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	public void Login(View view) {
		username = (EditText) findViewById(R.id.usernameField);
		password = (EditText) findViewById(R.id.passwordField);
		loginButton = (Button) findViewById(R.id.loginButton);

		usernameText = username.getText().toString();
		passwordText = password.getText().toString();

		if (usernameText.endsWith("@jcu.edu.au")
				&& passwordText.equals("admin")) {
			System.out.println("LecturerLogin");
			SaveSharedPreference.setUserName(this, usernameText);
			SaveSharedPreference.setPassword(this, passwordText);
			SaveSharedPreference.setUserType(this, "Lecturer");
			
			Intent lecturer = new Intent();
			lecturer.setClass(this, LecturerActivity.class);
			startActivity(lecturer);

		} else if (usernameText.endsWith("@my.jcu.edu.au")
				&& passwordText.equals("student")) {
			System.out.println("StudentLogin");
			SaveSharedPreference.setUserName(this, usernameText);
			SaveSharedPreference.setPassword(this, passwordText);
			SaveSharedPreference.setUserType(this, "Student");
			Intent student = new Intent();
			student.setClass(this, StudentActivity.class);
			startActivity(student);

		} else {
			attemptCount = attemptCount - 1;
			Toast.makeText(getApplicationContext(),
					"Login failed " + attemptCount + " attempts left",
					Toast.LENGTH_SHORT).show();
			if (attemptCount == 0) {

				Toast.makeText(getApplicationContext(),
						"Login disabled for 3 minutes", Toast.LENGTH_LONG)
						.show();

				loginButton.setEnabled(false);
			}
		}
	}
}
