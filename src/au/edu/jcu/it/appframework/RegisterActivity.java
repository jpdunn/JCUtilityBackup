package au.edu.jcu.it.appframework;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private static final String TAG = RegisterActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register(View view){
		
		EditText email = (EditText) findViewById(R.id.emailField);
		String emailText = email.getText().toString();
		
		if (emailText.endsWith("@my.jcu.edu.au") || emailText.endsWith("@jcu.edu.au")) {
			
			Log.i(TAG, emailText);
			Toast.makeText(getApplicationContext(), "Email Sent",
					   Toast.LENGTH_LONG).show();
			finish();
			
		}else{
			Toast.makeText(getApplicationContext(), "Invalid Email address",
					   Toast.LENGTH_LONG).show();

		}
		

	}
	
}
