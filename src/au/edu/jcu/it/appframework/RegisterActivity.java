package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.DBPopulator;
import au.edu.jcu.it.appframework.model.ServerCommFacade;
import au.edu.jcu.it.appframework.model.ServerCommunication;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class RegisterActivity extends Activity {

	ServerCommFacade serverComm;

	private static final String TAG = RegisterActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		serverComm = new ServerCommFacade();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register(View view) {

		EditText email = (EditText) findViewById(R.id.emailField);
		String emailText = email.getText().toString();
		EditText password = (EditText) findViewById(R.id.passwordField);
		String passwordText = password.getText().toString();

		if (emailText.endsWith("@my.jcu.edu.au")
				|| emailText.endsWith("@jcu.edu.au")) {
			Log.i(TAG, emailText);
			Log.i(TAG, passwordText);
			// Toast.makeText(getApplicationContext(), response,
			// Toast.LENGTH_LONG).show();


			// finish();

		} else {
			Toast.makeText(getApplicationContext(), "Invalid Email address",
					Toast.LENGTH_LONG).show();

		}

	}

}
