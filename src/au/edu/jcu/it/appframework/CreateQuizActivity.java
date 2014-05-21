package au.edu.jcu.it.appframework;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.SaveSharedPreference;
import au.edu.jcu.it.appframework.model.ServerCommFacade;

public class CreateQuizActivity extends Activity {
	
	ServerCommFacade serverComms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_quiz);
		
		serverComms = new ServerCommFacade();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_quiz, menu);
		return true;
	}
	
	public void submitQuiz(View view){
		
		TextView quizQuestion = (TextView) findViewById(R.id.quizQuestion);
		
		serverComms.addNewQuiz(quizQuestion.getText().toString(), "True", SaveSharedPreference.getUserName(this));
		
	}

}
