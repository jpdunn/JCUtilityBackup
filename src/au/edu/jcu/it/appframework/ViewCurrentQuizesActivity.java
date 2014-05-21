package au.edu.jcu.it.appframework;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class ViewCurrentQuizesActivity extends Activity {

	ServerCommFacade serverComms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_current_quizes);
		serverComms = new ServerCommFacade();
		System.out.println(serverComms.getCurrentQuizes("kim.ku@jcu.edu.au"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_current_quizes, menu);
		return true;
	}

}
