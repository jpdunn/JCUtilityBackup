package au.edu.jcu.it.appframework;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class StudentAttendClass extends Activity {
	
	ServerCommFacade serverComms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_attend_class);
		serverComms = new ServerCommFacade();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.student_attend_class, menu);
		return true;
	}
	
	public void sendQRCode(View view){
		TextView qrCode = (TextView) findViewById(R.id.QRCodeText);
		String response = serverComms.sendAttendanceCode(qrCode.getText().toString(), "brodie.bartley@gmail.com");
		
		System.out.println(response);
		
		if(response.toString().equals("REJECTED_DUPLICATE"))
		{
			Toast.makeText(getApplicationContext(), "You have already been marked present",
			Toast.LENGTH_LONG).show();
		}
		else if (response.toString().equals("ILLEGAL_CODE"))
		{
			Toast.makeText(getApplicationContext(), "You have scanned a unknown QR Code",
			Toast.LENGTH_LONG).show();
		}
		else if (response.toString().equals("VALID_CODE"))
		{
			Toast.makeText(getApplicationContext(), "You have been marked as present",
			Toast.LENGTH_LONG).show();
		}
			finish();
	}
}