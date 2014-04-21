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

public class MainActivity extends Activity {

	private EditText username, password;
	private String usernameText, passwordText;
	private Button loginButton;
	private static final String TAG = MainActivity.class.getName();
	int attemptCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void register(View view){
		
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	public void Login(View view) {
		username = (EditText) findViewById(R.id.usernameField);
		password = (EditText) findViewById(R.id.passwordField);
		loginButton = (Button) findViewById(R.id.loginButton);

		usernameText = username.getText().toString();
		passwordText = password.getText().toString();

		if (usernameText.endsWith("@jcu.edu.au") && passwordText.equals("admin")) {

			Log.i(TAG, usernameText);
			Log.i(TAG, passwordText);
			Intent lecturer = new Intent(this, LecturerActivity.class);
			startActivity(lecturer);


		} else if (usernameText.endsWith("@my.jcu.edu.au")
				&& passwordText.equals("student")) {

			Log.i(TAG, usernameText);
			Log.i(TAG, passwordText);
			Intent student = new Intent(this, StudentActivity.class);
			startActivity(student);

		}
		else{
			attemptCount = attemptCount + 1;
			Toast.makeText(getApplicationContext(), "Login failed",
					   Toast.LENGTH_SHORT).show();
			if (attemptCount == 3) {
			
				Toast.makeText(getApplicationContext(), "Login disabled for 3 minutes",
						   Toast.LENGTH_LONG).show();
				
				loginButton.setEnabled(false);
				
			}
			
		}
	}
}
